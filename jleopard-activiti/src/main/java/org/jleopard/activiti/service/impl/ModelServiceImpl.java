package org.jleopard.activiti.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.lang3.StringUtils;
import org.jleopard.activiti.service.ModelService;
import org.jleopard.activiti.utils.Pagination;
import org.jleopard.web.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public Model create(String name, String key, String category, String description) {
        //初始化一个空模型
        Model model = repositoryService.newModel();
        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        model.setName(name);
        model.setKey(key);
        model.setCategory(category);
        model.setMetaInfo(modelNode.toString());
        repositoryService.saveModel(model);
        String id = model.getId();
        //完善ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace",
                "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(id, editorNode.toString().getBytes(StandardCharsets.UTF_8));
        return model;
    }

    @Override
    public List<Model> findAll() {
        return repositoryService.createModelQuery().orderByCreateTime().asc().list();
    }

    @Override
    public Pagination<Model> findAll(int page, int size, String name) {
        Pagination<Model> pagination = new Pagination<>(page, size);

        ModelQuery modelQuery = repositoryService.createModelQuery();
        if (StringUtils.isNotBlank(name)) {
            modelQuery.modelNameLike(name);
        }
        int totalCount = (int) modelQuery.count();
        int totalPages = (int) Math.ceil(totalCount / size);
        pagination.setTotalElements(totalCount);
        pagination.setTotalPages(totalPages);
        List<Model> models = modelQuery.orderByCreateTime().asc().listPage(pagination.getStart(), pagination.getEnd());
        pagination.setContent(models);
        return pagination;
    }

    @Override
    public void deleteById(String id) {
        repositoryService.deleteModel(id);
    }

    @Override
    public void deployModel(String modelId) {
        //获取模型
        Model modelData = repositoryService.getModel(modelId);
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
        if (bytes == null) {
            throw new BadRequestException("模型数据为空，请先设计流程并成功保存，再进行发布。");
        }
        try {
            JsonNode modelNode = new ObjectMapper().readTree(bytes);
            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            if (model.getProcesses().size() == 0) {
                throw new BadRequestException("数据模型不符要求，请至少设计一条主线流程。");
            }
            //debug
            byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
//            System.out.println(new String(bpmnBytes, "UTF-8"));
            //发布流程
            String processName = modelData.getName() + ".bpmn20.xml";
//            DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
//                    .name(modelData.getName())
//                    .addString(processName, new String(bpmnBytes, "UTF-8"));
            DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                    .name(modelData.getName())
                    .addBpmnModel(processName, model);

            Deployment deployment = deploymentBuilder.deploy();
            modelData.setDeploymentId(deployment.getId());
            repositoryService.saveModel(modelData);
        } catch (Exception e) {
            throw new BadRequestException(e.toString());
        }
    }

    @Override
    public Model uploadModel(MultipartFile modelFile) {
        HashMap<String, Object> result = new HashMap<>();
        InputStreamReader in = null;
        try {
            try {
                boolean validFile = false;
                String fileName = modelFile.getOriginalFilename();
                assert fileName != null;
                if (fileName.endsWith(".bpmn20.xml") || fileName.endsWith(".bpmn")) {
                    validFile = true;
                    XMLInputFactory xif = createSafeXmlInputFactory();
                    in = new InputStreamReader(new ByteArrayInputStream(modelFile.getBytes()), "UTF-8");
                    XMLStreamReader xtr = xif.createXMLStreamReader(in);
                    BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
                    if (bpmnModel.getMainProcess() == null || bpmnModel.getMainProcess().getId() == null) {
//                        notificationManager.showErrorNotification(Messages.MODEL_IMPORT_FAILED,
//                                i18nManager.getMessage(Messages.MODEL_IMPORT_INVALID_BPMN_EXPLANATION));
                        throw new BadRequestException("数据模型无效，必须有一条主流程");
                    } else {
                        if (bpmnModel.getLocationMap().isEmpty()) {
//                            notificationManager.showErrorNotification(Messages.MODEL_IMPORT_INVALID_BPMNDI,
//                                    i18nManager.getMessage(Messages.MODEL_IMPORT_INVALID_BPMNDI_EXPLANATION));
                            throw new BadRequestException("locationMap为空");
                        } else {
                            String processName = null;
                            if (StringUtils.isNotEmpty(bpmnModel.getMainProcess().getName())) {
                                processName = bpmnModel.getMainProcess().getName();
                            } else {
                                processName = bpmnModel.getMainProcess().getId();
                            }
                            Model modelData;
                            modelData = repositoryService.newModel();
                            ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
                            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processName);
                            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
                            modelData.setMetaInfo(modelObjectNode.toString());
                            modelData.setName(processName);
                            repositoryService.saveModel(modelData);
                            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
                            ObjectNode editorNode = jsonConverter.convertToJson(bpmnModel);
                            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes(StandardCharsets.UTF_8));
                            return modelData;
                        }
                    }
                } else {
                    throw new BadRequestException("文件无效");
                }
            } catch (Exception e) {
                throw new BadRequestException(e.toString());
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private XMLInputFactory createSafeXmlInputFactory() {
        XMLInputFactory xif = XMLInputFactory.newInstance();
        if (xif.isPropertySupported("javax.xml.stream.isReplacingEntityReferences")) {
            xif.setProperty("javax.xml.stream.isReplacingEntityReferences", false);
        }

        if (xif.isPropertySupported("javax.xml.stream.isSupportingExternalEntities")) {
            xif.setProperty("javax.xml.stream.isSupportingExternalEntities", false);
        }

        if (xif.isPropertySupported("javax.xml.stream.supportDTD")) {
            xif.setProperty("javax.xml.stream.supportDTD", false);
        }
        return xif;
    }
}

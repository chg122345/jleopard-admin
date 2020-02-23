package org.jleopard.activiti.service;

import org.activiti.engine.repository.Model;
import org.jleopard.activiti.utils.Pagination;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/** 流程模型管理的接口 */
public interface ModelService {
    /** 新建一个模型 */
    Model create(String name, String key, String category, String description);

    /** 获取所有模型 */
   List<Model> findAll();

    /** 获取指定页码的模型 */
    Pagination<Model> findAll(int page, int size, String name);

    /** 删除指定模型 */
    void deleteById(String id);

    /** 部署模型 */
    void deployModel(String id);

    /** 上传已有xml文件，并生成相应模型*/
    Model uploadModel(MultipartFile modelFile);

}

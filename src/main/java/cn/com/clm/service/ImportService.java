package cn.com.clm.service;

import cn.com.clm.domain.People;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**

 * describe: import service
 *
 * @author liming.cao
 */
public interface ImportService {

    /**
     * import file
     *
     * @param file  file
     */
    List<People> importFile(MultipartFile file);
}

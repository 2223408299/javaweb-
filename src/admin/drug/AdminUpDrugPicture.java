package admin.drug;

import category.service.CategoryService;
import cn.itcast.commons.CommonUtils;
import drug.domain.Drug;
import drug.service.DrugService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/AdminUpDrugPicture")
public class AdminUpDrugPicture extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        /*
         * 1. commons-fileupload的上传三步
         */
        // 创建工具
        FileItemFactory factory = new DiskFileItemFactory();
        /*
         * 2. 创建解析器对象
         */
        ServletFileUpload sfu = new ServletFileUpload(factory);
        sfu.setFileSizeMax(1000 * 1024);//设置单个上传的文件上限为80KB

        /*
         * 3. 解析request得到List<FileItem>
         */
        List<FileItem> fileItemList = null;
        Drug drug = null;
        try {
            fileItemList = sfu.parseRequest(request);
        } catch (FileUploadException e) {
            // 如果出现这个异步，说明单个文件超出了80KB
            error("上传的文件超出了80KB", drug, request, response);
            return;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        for (FileItem fileItem : fileItemList) {
            if (fileItem.isFormField()) {//如果是普通表单字段
                map.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
            }
        }
        drug = CommonUtils.toBean(map, Drug.class);//把Map中大部分数据封装到Book对象中

        FileItem fileItem = fileItemList.get(1);//获取大图
        String filename = fileItem.getName();
        // 截取文件名，因为部分浏览器上传的绝对路径
        int index = filename.lastIndexOf("\\");
        if (index != -1) {
            filename = filename.substring(index + 1);
        }
        // 给文件名添加uuid前缀，避免文件同名现象
        filename = CommonUtils.uuid() + "_" + filename;
        // 校验文件名称的扩展名
        if (!filename.toLowerCase().endsWith(".jpg") && !filename.toLowerCase().endsWith(".jpeg")
                && !filename.toLowerCase().endsWith(".bmp") && !filename.toLowerCase().endsWith(".png")) {
            error("上传的图片扩展名必须是JPG或者JPEG或者BMP或者PNG", drug, request, response);
            return;
        }
        // 校验图片的尺寸
        // 保存上传的图片，把图片new成图片对象：Image、Icon、ImageIcon、BufferedImage、ImageIO
        /*
         * 保存图片：
         * 1. 获取真实路径
         */
        String savepath = this.getServletContext().getRealPath("/drug_images");
        /*
         * 2. 创建目标文件
         */
        File destFile = new File(savepath, filename);
        /*
         * 3. 保存文件
         */
        try {
            fileItem.write(destFile);//它会把临时文件重定向到指定的路径，再删除临时文件
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 把图片的路径设置给book对象
        drug.setImage_b("drug_images/" + filename);

        // 调用service完成保存
        DrugService drugService = new DrugService();
        //删除原来图片
        Drug drug2 = drugService.load(drug.getDrugId());
        String savepath2 = this.getServletContext().getRealPath("/");//获取真实的路径
        new File(savepath2, drug2.getImage_b()).delete();//删除文件

        drugService.upPicture(drug);
        // 保存成功信息转发到msg.jsp
//        request.setAttribute("msg", "添加商品成功！");
//        request.getRequestDispatcher("/adminjsps/msg.jsp").forward(request, response);
        request.getRequestDispatcher("/AdminDrugServlet?method=findAllDrug").forward(request, response);
    }

    /*
     * 保存错误信息，转发到add.jsp
     */
    private void error(String msg, Drug drug, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("msg", msg);
        request.setAttribute("drug", drug);
        request.setAttribute("parents", new CategoryService().findParents());//所有一级分类
        request.getRequestDispatcher("/adminjsps/admin/drug/add.jsp").
                forward(request, response);
    }
}

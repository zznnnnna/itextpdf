import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;

public class Pdf {
    public static void main(String[] args) throws Exception {
        Map map=new HashMap<>();
        map.put("name","奇怪的楠");
        map.put("sex","男");
        map.put("age","22");
        PdfCreate(map);
    }
    // 利用模板生成pdf
    public static void PdfCreate(Map map) throws Exception {
        // 模板路径
        String templatePath = "C:\\Users\\z\\Desktop\\pdfcreate\\模板演示.pdf";
        // 生成的新文件路径
        String newPDFPath = "C:\\Users\\z\\Desktop\\pdfcreate\\生成的pdf.pdf";
        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
            out = new FileOutputStream(newPDFPath);// 输出流
            reader = new PdfReader(templatePath);// 读取pdf模板
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();
            //字体   windows 10  自带  在 C:\Windows\Fonts 中放置
            String ttf = "/simhei.ttf";
            //设置字体格式
            BaseFont baseFont = BaseFont.createFont(ttf, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            int i = 0;
            java.util.Iterator<String> it = form.getFields().keySet().iterator();
            while (it.hasNext()) {
                String name = it.next().toString();
                //表单名
                System.out.print("表单 = " + name + "   ");
                //表单填的参数
                System.out.println("填充 = " + map.get(name));
                //测试字体
                //设置字体
                form.setFieldProperty(name, "textfont", baseFont, null);
                //设置字体大小 字体大小格式为 float
                form.setFieldProperty(name, "textsize", 8f, null);
                //往表单写属性
                //form.setField(name, str[i++]);
                form.setField(name, (String) map.get(name));
            }
            stamper.setFormFlattening(true);// 如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.close();
            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();
    }
}

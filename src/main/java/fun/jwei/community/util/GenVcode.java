package fun.jwei.community.util;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

@Component
public class GenVcode {

    private char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9'};
    private int charNum = codeSequence.length;
    private int width = 100;// 验证码图片的默认宽度
    private int height = 40; // 验证码图片的默认高度
    private String[] fontNames={"Bahnschrift","Comic Sans MS","Courier New","Gabriola","Ink Free"};


    public String generateVCode(OutputStream outputStream) throws IOException {
        // 首先定义验证码图片框

        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 定义图片上的图形和干扰线
        Graphics2D gd = buffImg.createGraphics();
        gd.setColor(new Color(0xded4df));
        gd.fillRect(0, 0, width, height);
//        gd.setColor(Color.BLACK); // 画边框。
        gd.drawRect(0, 0, width - 1, height - 1);
        Random random = new Random();

        // 随机产生35条灰色干扰线，使图像中的认证码不易识别
        for (int i = 0; i < 35; i++) {
            int red = random.nextInt(255);
            int green = random.nextInt(255);
            int blue = random.nextInt(255);
            gd.setColor(new Color(red, green, blue));
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(20);
            int yl = random.nextInt(20);
            gd.drawLine(x, y, x + xl, y + yl);
        }

        // 计算字的位置坐标
        int codeCount = 4; // 字符个数
        int fontHeight; // 字体高度
        int codeX; // 第一个字符的x坐标，因为后面的字符坐标依次递增，所以它们的x轴值是codeX的倍数
        int codeY; // 验证字符的y坐标，因为并排所以值一样
        // width-4 除去左右多余的位置，使验证码更加集中显示，减得越多越集中。
        // codeCount+1 //等比分配显示的宽度，包括左右两边的空格
        codeX = (width - 8) / codeCount; // 第一个字母的起始位置
        fontHeight = height - 10; // height - 10 高度中间区域显示验证码
        codeY = height - 5;


        String vcode = "";

        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < codeCount; i++) {
            // 创建字体，字体的大小应该根据图片的高度来定。
            int style = random.nextInt(10) % 2 == 0 ? 0 : 2;
            String fontName = fontNames[random.nextInt(fontNames.length)];
            Font font = new Font(fontName, style, fontHeight - random.nextInt(7));
            gd.setFont(font);

            // 每次随机拿一个字母，赋予随机的颜色
            String strRand = String.valueOf(codeSequence[random.nextInt(charNum)]);
            vcode += strRand;

            int red = random.nextInt(255);
            int green = random.nextInt(255);
            int blue = random.nextInt(255);

            //避免颜色太浅
            double light = red * 0.299 + green * 0.578 + blue * 0.114;
            while (light >= 192) {
                red = random.nextInt(255);
                green = random.nextInt(255);
                blue = random.nextInt(255);
                light = red * 0.299 + green * 0.578 + blue * 0.114;
            }


            gd.setColor(new Color(red, green, blue));
            // 把字放到图片上!!!
            gd.drawString(strRand, i * codeX + 2, codeY - random.nextInt(height - fontHeight));
        }
        ImageIO.setUseCache(false);
        ImageIO.write(buffImg, "jpg", outputStream);
        return vcode;
    }

}

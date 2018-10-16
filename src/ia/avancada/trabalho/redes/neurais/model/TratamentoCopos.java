/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia.avancada.trabalho.redes.neurais.model;

import ia.avancada.trabalho.redes.neurais.util.Log;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.neuroph.imgrec.FractionRgbData;
import org.neuroph.imgrec.ImageUtilities;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Arthur
 */
public class TratamentoCopos extends InterfaceCopos {

    public TratamentoCopos(String pasta) {
        super(pasta);
    }

    public void tratar(String saida) {
        Log.Debug(getClass().getName(), "Iniciando tratamento das imagens");

        for (Map.Entry<String, BufferedImage> entry : pastasCopos.entrySet()) {
            try {
                String key = entry.getKey();
                
                Mat mat = bufferedImageToMat(entry.getValue());
                
                Mat matF = filter(mat);
                BufferedImage imageFinal = ImageUtilities.resizeImage(Mat2BufferedImage(matF), 50, 100);
                
                File outputfile = new File(saida + "/" + key.substring(0, key.length()-1) + "/" + key.substring(key.length()-1, key.length()) + ".jpg");
                outputfile.mkdirs();
                System.out.println(saida + "/" + key.substring(0, key.length()-1) + "/" + key.substring(key.length()-1, key.length()) + ".jpg");
                ImageIO.write(imageFinal, "jpg", outputfile);
                
                
            } catch (Exception ex) {
                Logger.getLogger(TratamentoCopos.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public static Mat filter(final Mat src) {
        final Mat dst = new Mat(src.rows(), src.cols(), src.type());
        src.copyTo(dst);

        Imgproc.cvtColor(dst, dst, Imgproc.COLOR_BGR2GRAY);
        final List<MatOfPoint> points = new ArrayList<>();
        Imgproc.Canny(dst, dst, dst.width(), dst.height(), 5, false);

        List<MatOfPoint> contours = new ArrayList<>();
        Mat dest = Mat.zeros(dst.size(), CvType.CV_8UC3);
        Scalar white = new Scalar(255, 255, 255);

        Imgproc.findContours(dst, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        Point down = new Point(Double.MAX_VALUE, Double.MAX_VALUE);
        Point up = new Point(0, 0);

        for (MatOfPoint contour : contours) {
            Rect r = Imgproc.boundingRect(contour);

            if (r.x < down.x) {
                down.x = r.x;
            }

            if (r.y < down.y) {
                down.y = r.y;
            }

            if (r.x > up.x) {
                up.x = r.x;
            }

            if (r.y > up.y) {
                up.y = r.y;
            }
        }

        Rect f = new Rect(down, up);
        System.out.println(down);
        System.out.println(up);
        System.out.println(f);
        Imgproc.drawContours(dest, contours, -1, white);

        Mat cropedMat = new Mat(dest, f);
        return cropedMat;
    }

    public static Mat bufferedImageToMat(BufferedImage bi) {
        Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
        byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);
        return mat;
    }

    public static BufferedImage Mat2BufferedImage(Mat m) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (m.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels() * m.cols() * m.rows();
        byte[] b = new byte[bufferSize];
        m.get(0, 0, b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }

    public static void displayImage(Image img2) {
        ImageIcon icon = new ImageIcon(img2);
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(img2.getWidth(null) + 50, img2.getHeight(null) + 50);
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}

package Encrypt;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameUtils;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Random;

import static org.opencv.imgproc.Imgproc.threshold;

public class WaterMark {
    private File file;     // 加密/解密的路径
    public static final int add = 1;
    public static final int remove = 2;
    public JTextArea jTextArea;
    public WaterMark(File file,JTextArea jTextArea) {
        this.file = file;
        this.jTextArea =jTextArea;
    }
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    /**
     * Mat转换成BufferedImage
     * @param matrix   要转换的Mat
     * @param fileExtension  格式为 ".jpg", ".png", etc
     */
    public static BufferedImage Mat2BufImg (Mat matrix, String fileExtension) {
        // convert the matrix into a matrix of bytes appropriate for
        // this file extension
        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(fileExtension, matrix, mob);
        // convert the "matrix of bytes" into a byte array
        byte[] byteArray = mob.toArray();
        BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufImage;
    }

    /**
     * BufferedImage转换成Mat
     * @param original 要转换的BufferedImage
     * @param imgType bufferedImage的类型 如 BufferedImage.TYPE_3BYTE_BGR
     * @param matType 转换成mat的type 如 CvType.CV_8UC3
     */
    public static Mat BufImg2Mat (BufferedImage original, int imgType, int matType) {
        if (original == null) {
            throw new IllegalArgumentException("original == null");
        }
        // Don't convert if it already has correct type
        if (original.getType() != imgType) {
            // Create a buffered image
            BufferedImage image = new BufferedImage(original.getWidth(), original.getHeight(), imgType);
            // Draw the image onto the new buffer
            Graphics2D g = image.createGraphics();
            try {
                g.setComposite(AlphaComposite.Src);
                g.drawImage(original, 0, 0, null);
            } finally {
                g.dispose();
            }
        }
        byte[] pixels = ((DataBufferByte) original.getRaster().getDataBuffer()).getData();
        Mat mat = Mat.eye(original.getHeight(), original.getWidth(), matType);
        mat.put(0, 0, pixels);
        return mat;
    }
    public void dealWaterMark(int type){
        //抓取视频资源
        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(file);
        Frame frame = null;
        FFmpegFrameRecorder recorder = null;
        String fileName = null;
        try {
            frameGrabber.start();
            Random random = new Random();

            switch (type){
                case add:
                    fileName = "Encrypt\\" + random.nextInt(100) + ".avi";
                    jTextArea.append(fileName+"加密开始\n");
                    break;
                case remove:
                    fileName = "Decrypt\\" + random.nextInt(100) + ".avi";
                    jTextArea.append(fileName+"解密\n");
                    break;
            }
//            System.out.println("文件名-->>" + fileName);
            recorder = new FFmpegFrameRecorder(fileName, frameGrabber.getImageWidth(), frameGrabber.getImageHeight(),
                    frameGrabber.getAudioChannels());
            recorder.setSampleRate(frameGrabber.getSampleRate());
            recorder.setFrameRate(frameGrabber.getFrameRate());
            recorder.setTimestamp(frameGrabber.getTimestamp());
            recorder.setVideoBitrate(frameGrabber.getVideoBitrate());
            recorder.setVideoCodec(frameGrabber.getVideoCodec());
            recorder.start();
            while (true) {
                frame = frameGrabber.grabFrame();
                if (frame == null) {
                    System.out.println("视频处理完成");
                    break;
                }
                //判断图片帧
                if (frame.image != null) {
                    IplImage iplImage = Java2DFrameUtils.toIplImage(frame);
                    Frame newFrame = new Frame();
                    switch (type){
                        case add:
                            newFrame = Java2DFrameUtils.toFrame(addWaterMark(iplImage));
                            break;
                        case remove:
                            newFrame = Java2DFrameUtils.toFrame(removeWaterMark(iplImage));
                            break;
                    }
                    recorder.record(newFrame);
                }
            }
            jTextArea.append(fileName+"结束\n");
            recorder.stop();
            recorder.release();
            frameGrabber.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public BufferedImage addWaterMark(IplImage iplImage){
        BufferedImage buffImg = Java2DFrameUtils.toBufferedImage(iplImage);
        Graphics2D graphics = buffImg.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("宋体", Font.BOLD, 20));
        graphics.drawString("2021218381刘梦龙", (iplImage.width() / 2) + 100, iplImage.height() / 2+100);
        graphics.dispose();
        return buffImg;
    }
    public BufferedImage removeWaterMark(IplImage iplImage){
        BufferedImage buffImg = Java2DFrameUtils.toBufferedImage(iplImage);
        Mat img = BufImg2Mat(buffImg,BufferedImage.TYPE_3BYTE_BGR, CvType.CV_8UC3);
        Mat gray = new Mat();
        Imgproc.cvtColor(img, gray, Imgproc.COLOR_BGRA2GRAY);
        // 高斯模糊
        Imgproc.GaussianBlur(gray, gray, new Size(3, 3), 0);
        int height = img.height();
        int width = img.width();
        // 图片二值化处理，把[240, 240, 240] ~ [255, 255, 255]以外的颜色变成0
        Mat thresh = new Mat();
        // 二值化处理
        // inRange(img, new Scalar(240, 240, 240), new Scalar(255, 255, 255), thresh);
        // 二值化处理 ,两种方式都可以实现二值化操作
        threshold(gray, thresh, 0, 255,  Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
        // 创建形状和尺寸的结构元素
        Mat hiMask = new Mat();
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));

        // 对Mask膨胀处理, 扩张待修复区域
        Imgproc.dilate(thresh, hiMask, kernel);
        //图像修复
        Mat specular = new Mat();
        Photo.inpaint(img, hiMask, specular, 5, Photo.INPAINT_TELEA);
        return Mat2BufImg(specular,".jpg");
    }
    public static void main(String[] args) {
    }
}

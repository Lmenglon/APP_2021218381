package Frame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class FileChoose {
    private Component parent;
    public FileChoose(Component parent) {
        this.parent = parent;
    }
    public File[] showFilesOpenDialog() {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();
        // 设置默认显示的文件夹为当前文件夹
        fileChooser.setCurrentDirectory(new File("."));
        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        // 设置是否允许多选
        fileChooser.setMultiSelectionEnabled(true);
        // 设置默认使用的文件过滤器
        fileChooser.setFileFilter(new FileNameExtensionFilter("audio(*.wav)", "wav"));
        // 添加可用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("video(*.avi)", "avi"));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showOpenDialog(parent);
        File[] files = new File[0];
        if (result == JFileChooser.APPROVE_OPTION) {
            // File file = fileChooser.getSelectedFile();
            // 如果允许选择多个文件, 则通过下面方法获取选择的所有文件
            files = fileChooser.getSelectedFiles();
        }
        return files;
    }
    public File showDirectoryOpenDialog() {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();
        // 设置默认显示的文件夹为当前文件夹
        fileChooser.setCurrentDirectory(new File("."));
        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setCurrentDirectory(new File("."));
        // 设置是否允许多选
        fileChooser.setMultiSelectionEnabled(false);
        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showOpenDialog(parent);
        File file = null;
        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"确定", 则获取选择的文件路径
             file = fileChooser.getSelectedFile();
        }
        return file;
    }
    public File showFileOpenDialog() {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();
        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setCurrentDirectory(new File("."));
        // 设置是否允许多选
        fileChooser.setFileFilter(new FileNameExtensionFilter("图片(*.jpg)", "jpg"));
        fileChooser.setMultiSelectionEnabled(false);
        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showOpenDialog(parent);
        File file = null;
        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"确定", 则获取选择的文件路径
            file = fileChooser.getSelectedFile();
        }
        return file;
    }
}

package huihuang.proxy.ocpx.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2024-06-30 11:54
 **/
public class TextSplitterWithOffsets {

    public static void main(String[] args) {
        String text = "在绿茵场上，两所学府的学子们正展开激烈角逐，好戏连台。突然，一个身手矫健的青年球员吸引了场边乡绅纳兰天的眼球，原来这位风头正劲的小伙子，正是黄麒英的公子，黄飞鸿是也。";
        List<List<Object>> subtitles = new ArrayList<>();
        subtitles.add(Arrays.asList(0.1, 0.388, "在"));
        subtitles.add(Arrays.asList(0.4, 0.85, "绿茵场"));
        subtitles.add(Arrays.asList(0.85, 1.075, "上"));
        subtitles.add(Arrays.asList(1.225, 1.587, "两所"));
        subtitles.add(Arrays.asList(1.6, 1.9, "学府"));
        subtitles.add(Arrays.asList(1.9, 1.975, "的"));
        subtitles.add(Arrays.asList(1.988, 2.288, "学子"));
        subtitles.add(Arrays.asList(2.288, 2.462, "们"));
        subtitles.add(Arrays.asList(2.525, 2.725, "正"));
        subtitles.add(Arrays.asList(2.725, 3.1, "展开"));
        subtitles.add(Arrays.asList(3.125, 3.438, "激烈"));
        subtitles.add(Arrays.asList(3.438, 3.875, "角逐"));
        subtitles.add(Arrays.asList(3.987, 4.862, "好戏连台"));
        subtitles.add(Arrays.asList(5.438, 5.912, "突然"));
        subtitles.add(Arrays.asList(6.037, 6.35, "一个"));
        subtitles.add(Arrays.asList(6.362, 6.688, "身手"));
        subtitles.add(Arrays.asList(6.7, 7.037, "矫健"));
        subtitles.add(Arrays.asList(7.037, 7.138, "的"));
        subtitles.add(Arrays.asList(7.162, 7.425, "青年"));
        subtitles.add(Arrays.asList(7.438, 7.912, "球员"));
        subtitles.add(Arrays.asList(8.025, 8.375, "吸引"));
        subtitles.add(Arrays.asList(8.375, 8.512, "了"));
        subtitles.add(Arrays.asList(8.537, 8.787, "场"));
        subtitles.add(Arrays.asList(8.787, 8.963, "边"));
        subtitles.add(Arrays.asList(8.975, 9.387, "乡绅"));
        subtitles.add(Arrays.asList(9.412, 9.825, "纳兰天"));
        subtitles.add(Arrays.asList(9.825, 9.887, "的"));
        subtitles.add(Arrays.asList(9.9, 10.438, "眼球"));
        subtitles.add(Arrays.asList(10.688, 11.037, "原来"));
        subtitles.add(Arrays.asList(11.05, 11.162, "这"));
        subtitles.add(Arrays.asList(11.162, 11.312, "位"));
        subtitles.add(Arrays.asList(11.338, 11.738, "风头"));
        subtitles.add(Arrays.asList(11.738, 12.05, "正劲"));
        subtitles.add(Arrays.asList(12.05, 12.125, "的"));
        subtitles.add(Arrays.asList(12.137, 12.738, "小伙子"));
        subtitles.add(Arrays.asList(12.887, 13.275, "正是"));
        subtitles.add(Arrays.asList(13.3, 13.812, "黄麒英"));
        subtitles.add(Arrays.asList(13.812, 13.912, "的"));
        subtitles.add(Arrays.asList(13.925, 14.338, "公子"));
        subtitles.add(Arrays.asList(14.488, 15.188, "黄飞鸿"));
        subtitles.add(Arrays.asList(15.2, 15.375, "是"));
        subtitles.add(Arrays.asList(15.375, 15.562, "也"));

        List<String> splitTexts = splitText(text);
        System.out.println(splitTexts);
        for (String splitText : splitTexts) {
            findOffsets(splitText, subtitles);
        }
    }

    public static List<String> splitText(String text) {
        List<String> parts = new ArrayList<>();
        String[] splitByComma = text.split(",");
        for (String part : splitByComma) {
            String[] splitByPeriod = part.split("。");
            for (String subPart : splitByPeriod) {
                parts.add(subPart.trim());
            }
        }
        return parts;
    }

    public static void findOffsets(String text, List<List<Object>> subtitles) {
        for (List<Object> subtitle : subtitles) {
            if (subtitle.get(2).equals(text)) {
                System.out.println("文本: " + text + ", 开始偏移量: " + subtitle.get(0) + ", 结束偏移量: " + subtitle.get(1));
                break;
            }
        }
    }


}

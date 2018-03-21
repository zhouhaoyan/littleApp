package com.megatron.picserver.utils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class ImageUtil {
	
	/**
     * 缩放图像（按高度和宽度缩放）
     * @param srcImageFile 源图像文件地址
     * @param result 缩放后的图像地址
     * @param height 缩放后的高度
     * @param width 缩放后的宽度
     * @param bb 比例不对时是否需要补白：true为补白; false为不补白;
     * @author lkl
     */
    public final static void scale(String srcImageFile, String result, int height, int width, boolean bb) {
        try {
            double ratio = 0.0; // 缩放比例
            File f = new File(srcImageFile);
            BufferedImage bi = ImageIO.read(f);
            Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
            // 计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (new Integer(height)).doubleValue()
                            / bi.getHeight();
                } else {
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);
            }
            if (bb) {//补白
                BufferedImage image = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null))
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                else
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                g.dispose();
                itemp = image;
            }
            ImageIO.write((BufferedImage) itemp, "JPEG", new File(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 缩放图像（按高度和宽度缩放）补白、并打水印
     * @param pressImg 水印图片
     * @param srcImageFile 源图像地址
     * @param destImageFile 目标图像地址
     * @param width  宽度 //设置宽度时（高度传入0，等比例缩放）  
     * @param height 高度 //设置高度时（宽度传入0，等比例缩放）  
     * @param quality 质量 
     * @param x 修正值。 默认在中间
     * @param y 修正值。 默认在中间
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @param bb	比例不对时是否需要补白：true为补白; false为不补白;
     * @author lkl
     */
    public final static void scalePressImage(String pressImg, String srcImageFile, String destImageFile, int height, int width, float quality, int x, int y, float alpha, boolean bb) {
    	try {
    		double ratio = 0.0; // 缩放比例
    		File f = new File(srcImageFile);
    		BufferedImage bi = ImageIO.read(f);
    		Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
    		// 计算比例
    		if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
    			if (bi.getHeight() > bi.getWidth()) {
    				ratio = (new Integer(height)).doubleValue()
    						/ bi.getHeight();
    			} else {
    				ratio = (new Integer(width)).doubleValue() / bi.getWidth();
    			}
    			AffineTransformOp op = new AffineTransformOp(AffineTransform
    					.getScaleInstance(ratio, ratio), null);
    			itemp = op.filter(bi, null);
    		}
    		if (bb) {
    			//补白
    			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    			Graphics2D g = image.createGraphics();
    			g.setColor(Color.white);
    			g.fillRect(0, 0, width, height);
    			if (width == itemp.getWidth(null))
    				g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
    			else
    				g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
    			// 补白结束
    			
    			// 水印文件
                Image src_biao = ImageIO.read(new File(pressImg));
                int wideth_biao = src_biao.getWidth(null);
                int height_biao = src_biao.getHeight(null);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                        alpha));
                g.drawImage(src_biao,(width - wideth_biao) / 2  , (height - height_biao) / 2, wideth_biao, height_biao, null);
                // 水印文件结束
        		g.dispose();
    			itemp = image;
    		}
    		ImageIO.write((BufferedImage) itemp, "JPEG", new File(destImageFile));
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    /**
     * 缩放图像（按高度和宽度缩放）补白、并打水印
     * @param pressImg 水印图片
     * @param srcImageFile 源图像地址
     * @param destImageFile 目标图像地址
     * @param width  宽度 //设置宽度时（高度传入0，等比例缩放）  
     * @param height 高度 //设置高度时（宽度传入0，等比例缩放）  
     * @param quality 质量 
     * @param x 修正值。 默认在中间
     * @param y 修正值。 默认在中间
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @param bb	比例不对时是否需要补白：true为补白; false为不补白;
     * @param isPress	是否打水印：true为打; false为不打;
     */
    public final static void scale2PressImage(String pressImg, String srcImageFile, String destImageFile, int width, int height,
    		float quality, int x, int y, float alpha, boolean bb, boolean isPress) {
    	try {
    		double ratio = 0.0; // 缩放比例
    		File f = new File(srcImageFile);
    		BufferedImage bi = ImageIO.read(f);
    		int imgw = bi.getWidth();
    		int imgh = bi.getHeight();
    		
    		if((width+height)==0){
    			width = imgw;
    			height = imgh;
    		}else if(height==0){
    			height = (imgh*width)/imgw;
    		}
    		Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
    		
    		if (imgw < width && imgh < height) {
				ImageUtil.scale3PressImage(pressImg, srcImageFile, destImageFile, width, height, quality, x, y, alpha, bb, isPress);
			} else {
				// 计算比例
				if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
					if (bi.getHeight() > bi.getWidth()) {
						ratio = (new Integer(height)).doubleValue()
								/ bi.getHeight();
					} else {
						ratio = (new Integer(width)).doubleValue() / bi.getWidth();
					}
					AffineTransformOp op = new AffineTransformOp(AffineTransform
							.getScaleInstance(ratio, ratio), null);
					itemp = op.filter(bi, null);
				}
				if (bb) {
					//补白
					BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
					Graphics2D g = image.createGraphics();
					g.setColor(Color.white);
					g.fillRect(0, 0, width, height);
					if (width == itemp.getWidth(null))
						g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
					else
						g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
					// 补白结束
					if (isPress) {
						// 水印文件
						Image src_biao = ImageIO.read(new File(pressImg));
						int wideth_biao = src_biao.getWidth(null);
						int height_biao = src_biao.getHeight(null);
						g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
								alpha));
						g.drawImage(src_biao,(width - wideth_biao) / 2  , (height - height_biao) / 2, wideth_biao, height_biao, null);
						// 水印文件结束
					}
					g.dispose();
					itemp = image;
				}
				ImageIO.write((BufferedImage) itemp, "JPEG", new File(destImageFile));
			}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    /**
     * 图片尺寸达不到设置的宽高，补白、并打水印
     * @param pressImg 水印图片
     * @param srcImageFile 源图像地址
     * @param destImageFile 目标图像地址
     * @param width  宽度 //设置宽度时（高度传入0，等比例缩放）  
     * @param height 高度 //设置高度时（宽度传入0，等比例缩放）  
     * @param quality 质量 
     * @param x 修正值。 默认在中间
     * @param y 修正值。 默认在中间
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     * @param bb	比例不对时是否需要补白：true为补白; false为不补白;
     * @param isPress	是否打水印：true为打; false为不打;
     */
    public final static void scale3PressImage(String pressImg, String srcImageFile, String destImageFile, int width, int height,
    		float quality, int x, int y, float alpha, boolean bb, boolean isPress) {
    	try {
    		File f = new File(srcImageFile);
    		BufferedImage bi = ImageIO.read(f);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.setColor(Color.white);
			g.fillRect(0, 0, width, height);
			g.drawImage(bi, (width - bi.getWidth()) / 2, (height - bi.getHeight()) / 2,
					bi.getWidth(), bi.getHeight(),
					Color.white, null);
    		if (isPress) {
    			Image src_biao = ImageIO.read(new File(pressImg));
    			int wideth_biao = src_biao.getWidth(null);
    			int height_biao = src_biao.getHeight(null);
    			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
    			g.drawImage(src_biao,(width - wideth_biao) / 2  , (height - height_biao) / 2, wideth_biao, height_biao, null);
    			// 水印文件结束
			}
			// 水印文件
			g.dispose();
			
    		ImageIO.write(image, "JPEG", new File(destImageFile));
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
	/**
	 * 根据尺寸图片居中裁剪
	 * @param src	原图地址
	 * @param dest	目标文件地址
	 * @param w	裁剪目标文件宽度
	 * @param h 裁剪目标文件宽度
	 * @throws IOException
	 * @author lkl
	 */
	public static void cutCenterImage(String src, String dest, int w, int h)
			throws IOException {
		Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");
		ImageReader reader = (ImageReader) iterator.next();
		InputStream in = new FileInputStream(src);
		ImageInputStream iis = ImageIO.createImageInputStream(in);
		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();
		int imageIndex = 0;
		Rectangle rect = new Rectangle((reader.getWidth(imageIndex) - w) / 2,
				(reader.getHeight(imageIndex) - h) / 2, w, h);
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0, param);
		ImageIO.write(bi, "jpg", new File(dest));

	}

	/**
	 * 图片裁剪二分之一
	 * @param src	
	 * @param dest
	 * @throws IOException
	 */
	public static void cutHalfImage(String src, String dest) throws IOException {
		Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");
		ImageReader reader = (ImageReader) iterator.next();
		InputStream in = new FileInputStream(src);
		ImageInputStream iis = ImageIO.createImageInputStream(in);
		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();
		int imageIndex = 0;
		int width = reader.getWidth(imageIndex) / 2;
		int height = reader.getHeight(imageIndex) / 2;
		Rectangle rect = new Rectangle(width / 2, height / 2, width, width);
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0, param);
		ImageIO.write(bi, "jpg", new File(dest));
	}
	
	/**
     * 设置机构联系人名片（图片形式）
     * @param srcImageFile	原图片地址
     * @param destImageFile	名片地址
     * @param name	名片所属人姓名
     * @param sex	名片所属人性别
     * @param title	名片所属人职称
     * @param mobile	所属人手机号
     * @param companyName	所在机构名称
     * @param telPhone	所属人联系电话
     * @param address	所属机构地址
     * @param fontName	字体名称
     * @param alpha
     */
    public final static void cardImage(String srcImageFile, String destImageFile,
            String name, String sex,String title, String mobile, String companyName, String telPhone, String address,
            String fontName,float alpha) {
    	try {
    		File img = new File(srcImageFile);
    		Image src = ImageIO.read(img);
    		int width = src.getWidth(null);
    		int height = src.getHeight(null);
    		BufferedImage image = new BufferedImage(width, height,
    				BufferedImage.TYPE_INT_RGB);
    		Graphics2D g = image.createGraphics();
    		g.drawImage(src, 0, 0, width, height, null);
    		Color color = new Color(70, 175, 215);
    		// --------------- 所属人名称 begin --------------- 
    		int x = 50;
    		int y = 60; 
    		Integer fontSize = 25;
    		g.setColor(color);
            g.setFont(new Font(fontName, Font.BOLD, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 在指定坐标绘制水印文字
            g.drawString(name, x, y);
            // --------------- 所属人名称 end --------------- 
            
            // --------------- 所属人性别 begin --------------- 
            int sexX = 50 + getLength(name) * fontSize + 20;
            y = 60; 
            fontSize = 20;
            g.setColor(new Color(70, 175, 215));
            g.setFont(new Font(fontName, Font.BOLD, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 在指定坐标绘制水印文字
            g.drawString(sex, sexX, y);
            // --------------- 所属人性别 end --------------- 
            
            // --------------- 所属人职称 begin --------------- 
            x = sexX + 50;
            y = 60; 
            fontSize = 20;
            g.setColor(color);
            g.setFont(new Font(fontName, Font.BOLD, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 在指定坐标绘制水印文字
            g.drawString(title, x,y);
            // --------------- 所属人职称 end --------------- 
            
            // --------------- 所属人手机号 begin --------------- 
            x = 50;
            y = 105; 
            fontSize = 20;
            g.setColor(Color.black);
            g.setFont(new Font(fontName, Font.BOLD, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            mobile = "手机：" + mobile;
            // 在指定坐标绘制水印文字
            g.drawString(mobile, x,y);
            // --------------- 所属人手机号 end --------------- 
            
            // --------------- 所在机构名称 begin --------------- 
            x = 50;
            y = 175; 
            fontSize = 25;
            g.setColor(Color.white);
            g.setFont(new Font(fontName, Font.BOLD, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 在指定坐标绘制水印文字
            g.drawString(companyName, x,y);
            // --------------- 所在机构名称 end --------------- 
            
            // --------------- 所属人联系电话 begin --------------- 
            x = 50;
            y = 240; 
            fontSize = 20;
            g.setColor(Color.black);
            g.setFont(new Font(fontName, Font.BOLD, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            telPhone = "电话：" + telPhone;
            // 在指定坐标绘制水印文字
            g.drawString(telPhone, x,y);
            // --------------- 所属人联系电话 end --------------- 
            
            // --------------- 所属机构地址 begin --------------- 
            x = 50;
            y = 280; 
            fontSize = 20;
            g.setColor(Color.black);
            g.setFont(new Font(fontName, Font.BOLD, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            address = "地址：" + address;
            // 在指定坐标绘制水印文字
            g.drawString(address, x,y);
            // --------------- 所属机构地址 end --------------- 
    		
    		g.dispose();
    		ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));// 输出到文件流
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

	/*
	 * 图片裁剪通用接口
	 */
	public static void cutImage(String src, String dest, int x, int y, int w,
			int h) throws IOException {
		Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");
		ImageReader reader = (ImageReader) iterator.next();
		InputStream in = new FileInputStream(src);
		ImageInputStream iis = ImageIO.createImageInputStream(in);
		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();
		Rectangle rect = new Rectangle(x, y, w, h);
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0, param);
		ImageIO.write(bi, "jpg", new File(dest));
	}

	/**
	 * 图片水印
	 * 
	 * @param pressImg 水印图片
     * @param srcImageFile 源图像地址
     * @param destImageFile 目标图像地址
	 * @param x 修正值 默认在中间
	 * @param y 修正值 默认在中间
	 * @param alpha 透明度
	 */
	public final static void pressImage(String pressImg, String srcImageFile, String destImageFile,
			int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            // 水印文件
            Image src_biao = ImageIO.read(new File(pressImg));
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            g.drawImage(src_biao, (wideth - wideth_biao) / 2,
                    (height - height_biao) / 2, wideth_biao, height_biao, null);
            // 水印文件结束
            g.dispose();
            ImageIO.write((BufferedImage) image,  "JPEG", new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 文字水印
	 * 
	 * @param pressText  水印文字
	 * @param targetImg 目标图片
	 * @param fontName 字体名称
	 * @param fontStyle 字体样式
	 * @param color  字体颜色
	 * @param fontSize 字体大小
	 * @param x 修正值
	 * @param y 修正值
	 * @param alpha 透明度
	 */
	public static void pressText(String pressText, String targetImg,
			String fontName, int fontStyle, Color color, int fontSize, int x,
			int y, float alpha) {
		try {
			File img = new File(targetImg);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			g.drawString(pressText, (width - (getLength(pressText) * fontSize))
					/ 2 + x, (height - fontSize) / 2 + y);
			g.dispose();
			ImageIO.write((BufferedImage) image, "jpg", img);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length / 2;
	}

	/**
	 * 获取新图片名
	 * 
	 * @param filePath
	 * @param str
	 * @return
	 */
	public static String newFilePath(String filePath, String str) {
		String kz = filePath.substring(filePath.lastIndexOf("."),
				filePath.length());
		String fileState = filePath.substring(0, filePath.lastIndexOf("."));
		return fileState + "_" + str + kz;
	}

	public static Integer[] newImageWH(Integer isWH, String str) {
		String[] strs = str.split("x");
		Integer[] whArr = new Integer[strs.length];
		if (isWH.equals(1)) {
			whArr[0] = Integer.valueOf(strs[0]);
			whArr[1] = null;
		} else {
			whArr[0] = Integer.valueOf(strs[1]);
			whArr[1] = null;
		}
		return whArr;
	}

	public static Integer getImageNewWH(Integer isWH, String str) {
		String[] strs = str.split("x");
		Integer wh = 0;
		if (isWH.equals(1)) {
			wh = Integer.valueOf(strs[0]);
		} else {
			wh = Integer.valueOf(strs[1]);
		}
		return wh;
	}

	/**
	 * 获取以什么等比缩放
	 * 
	 * @param width
	 * @param height
	 * @return 1根据宽 2根据高
	 */
	public static Integer isWvsH(Integer width, Integer height) {
		Integer iswh = 1;
		if (width > height) {
			iswh = 1;
		} else if (width < height) {
			iswh = 2;
		} else {
			iswh = 1;
		}
		return iswh;
	}

	/**
	 * 旋转图片为指定角度
	 * @param targetImg 目标图像
	 * @param angel  旋转角度
	 * @return
	 * @throws IOException
	 */
	public static void rotateImage(String targetImg, int angel) {
		try {
			File img = new File(targetImg);
			Image src = ImageIO.read(img);
			ImageIO.write((BufferedImage) rotate(src, angel), "jpg", img);
		} catch (Exception e) {
			System.out.println("旋转图片出现错误ImageUtil.rotateImage");
		}
	}
	
	/**
	 * 旋转图片为指定角度
	 * 
	 * @param targetImg
	 *            目标图像
	 * @param angel
	 *            旋转角度
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage rotate(String targetImg, int angel)
			throws IOException {
		File img = new File(targetImg);
		
		return rotate(img, angel);
	}
	
	/**
	 * 旋转图片为指定角度
	 * 
	 * @param img 目标图像
	 * @param angel  旋转角度
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage rotate(File img, int angel)
			throws IOException {
		Image src = ImageIO.read(img);
		
		return rotate(src, angel);
	}

	/**
	 * 旋转图片为指定角度
	 * 
	 * @param src
	 *            目标图像
	 * @param angel
	 *            旋转角度
	 * @return
	 */
	public static BufferedImage rotate(Image src, int angel) {
		int src_width = src.getWidth(null);
		int src_height = src.getHeight(null);
		if(angel>360){
			angel = angel%360;
		}
		if (angel < 0) {
			angel = angel + 360;
		}
		// calculate the new image size
		Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(
				src_width, src_height)), angel);

		BufferedImage res = null;
		res = new BufferedImage(rect_des.width, rect_des.height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = res.createGraphics();
		// transform
		g2.translate((rect_des.width - src_width) / 2,
				(rect_des.height - src_height) / 2);
		g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);

		g2.drawImage(src, null, null);
		return res;
	}

	public static Rectangle CalcRotatedSize(Rectangle src, int angel) {
		// if angel is greater than 90 degree, we need to do some conversion
		if (angel >= 90) {
			if (angel / 90 % 2 == 1) {
				int temp = src.height;
				src.height = src.width;
				src.width = temp;
			}
			angel = angel % 90;
		}

		double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
		double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
		double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
		double angel_dalta_width = Math.atan((double) src.height / src.width);
		double angel_dalta_height = Math.atan((double) src.width / src.height);

		int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha
				- angel_dalta_width));
		int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha
				- angel_dalta_height));
		int des_width = src.width + len_dalta_width * 2;
		int des_height = src.height + len_dalta_height * 2;
		return new Rectangle(new Dimension(des_width, des_height));
	}

	public static void main(String[] args) throws IOException {

		ImageUtil.scale2PressImage("E:/images/testZipImage/zipimgtest/watermark2.png", "E:/images/testZipImage/zipimgtest/test5/1.jpg", "E:/images/testZipImage/zipimgtest/test5/1-test2-(4-3)-1000X750.jpg", 1000, 750, 1, 0, 0, 0.5f, true, false);
		ImageUtil.scale2PressImage("E:/images/testZipImage/zipimgtest/watermark2.png", "E:/images/testZipImage/zipimgtest/test5/1.jpg", "E:/images/testZipImage/zipimgtest/test5/a1-test2-(4-3)-1000X750.0.5.jpg", 1000, 750, 1, 0, 0, 0.5f, true, true);
		ImageUtil.scale2PressImage("E:/images/testZipImage/zipimgtest/watermark2.png", "E:/images/testZipImage/zipimgtest/test5/1-test2-(4-3)-1000X750.jpg", "E:/images/testZipImage/zipimgtest/test5/a1-test2-(4-3)-600X450.0.5.jpg", 600, 450, 1, 0, 0, 0.5f, true, true);
		ImageUtil.scale2PressImage("E:/images/testZipImage/zipimgtest/watermark1.png", "E:/images/testZipImage/zipimgtest/test5/1-test2-(4-3)-1000X750.jpg", "E:/images/testZipImage/zipimgtest/test5/a1-test1-(4-3)-400X300.jpg", 400, 300, 1, 0, 0, 0.5f, true, false);
		ImageUtil.scale2PressImage("E:/images/testZipImage/zipimgtest/watermark2.png", "E:/images/testZipImage/zipimgtest/test5/1.jpg", "E:/images/testZipImage/zipimgtest/test5/a1-test2-(4-3)-1000X750.0.5.1.jpg", 1000, 750, 1, 0, 0, 0.5f, true, true);
		ImageUtil.scale2PressImage("E:/images/testZipImage/zipimgtest/watermark2.png", "E:/images/testZipImage/zipimgtest/test5/1.jpg", "E:/images/testZipImage/zipimgtest/test5/a1-test2-(4-3)-600X450.0.5.1.jpg", 600, 450, 1, 0, 0, 0.5f, true, true);
		ImageUtil.scale2PressImage("E:/images/testZipImage/zipimgtest/watermark1.png", "E:/images/testZipImage/zipimgtest/test5/1.jpg", "E:/images/testZipImage/zipimgtest/test5/a1-test1-(4-3)-400X300.1.jpg", 400, 300, 1, 0, 0, 0.5f, true, false);
	}

}

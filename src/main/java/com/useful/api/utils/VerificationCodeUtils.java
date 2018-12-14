package com.useful.api.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class VerificationCodeUtils {
	
	protected static String RSA_PUBKEY="rsaPubKey";
	protected static String RSA_PRIKEY="rsaPriKey";
	protected static String RSA_EXPO="rsaExponent";
	protected static String RSA_MODU="rsaModulus";
	
	public static void initRSA(HttpServletRequest request) throws NoSuchAlgorithmException {
        HttpSession session = request.getSession();
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);  
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey pubKey = (RSAPublicKey)keyPair.getPublic();
        RSAPrivateKey priKey = (RSAPrivateKey)keyPair.getPrivate();
		session.setAttribute(RSA_PUBKEY,pubKey);
		session.setAttribute(RSA_PRIKEY,priKey);
		session.setAttribute(RSA_EXPO,pubKey.getPublicExponent().toString(16));
		session.setAttribute(RSA_MODU, pubKey.getModulus().toString(16));
    }
	
	public static String generateCode(int ii) {
        String[] beforeShuffle = new String[] { "2", "3", "4", "5", "6", "7",
                "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "J",  
                "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V",  
                "W", "X", "Y", "Z","a","b","c","h","m","x","s" };  
        List<String> list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {  
            sb.append(list.get(i));  
        }  
        String afterShuffle = sb.toString();
        String result = afterShuffle.substring(5, 5+ii);
        return result;  
    }
	
	public static BufferedImage generateImage(String s) {
		try {
			int width = 80;
			int height = 25;
			// 取得一个4位随机字母数字字符串
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = image.getGraphics();
			// 设定背景色
			g.setColor(getRandColor(200, 250));
			g.fillRect(0, 0, width, height);
			// 设定字体
			Font mFont =new Font("Fixedsys", Font.PLAIN, height);
			g.setFont(mFont);
			// 画边框
			// g.setColor(Color.BLACK);
			// g.drawRect(0, 0, width - 1, height - 1);
			// 随机产生干扰线，使图象中的认证码不易被其它程序探测到
			g.setColor(getRandColor(160, 200));
			// 生成随机类
			Random random = new Random();
			for (int i = 0; i < 155; i++) {
				int x2 = random.nextInt(width);
				int y2 = random.nextInt(height);
				int x3 = random.nextInt(12);
				int y3 = random.nextInt(12);
				g.drawLine(x2, y2, x2 + x3, y2 + y3);
			}
			// 将认证码显示到图象中
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));

			g.drawString(s, 4, 20);
			// 图象生效
			g.dispose();
			// 输出图象到页面
			return image;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static Color getRandColor(int fc, int bc) { // 给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255)fc = 255;
		if (bc > 255)bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	

}

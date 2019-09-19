package com.selene.common.util;

import static cn.com.lemon.base.Preasserts.checkArgument;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.coobird.thumbnailator.Thumbnails;

/**
 * The utilities for images process
 * 
 * @author shaobo shih
 * @version 1.0
 */
public final class Images {
	private static final Logger LOG = LoggerFactory.getLogger(Images.class.getName());

	private Images() {
	}

	/**
	 * Limited image height
	 * 
	 * @param source
	 * @param width
	 * @param target
	 * @return
	 */
	public static void height(String source, int height) {
		width(source, height, source);
	}

	public static void height(String source, int height, String target) {
		width(new File(source), height, new File(target));
	}

	public static void height(File source, int height, File target) {
		checkArgument((source.exists() && source.isFile()) && (target.exists() && target.isFile()),
				"The image file is not exist");
		int[] size = size(source);
		int w = size[0];
		int h = size[1];
		height = (height > h) ? h : height;
		int /* New height */ width = (w * height) / h;
		try {
			Thumbnails.of(source).size(width, height).toFile(target);
		} catch (IOException e) {
			LOG.error("Limited image width error!", e);
		}
	}

	/**
	 * Limited image width
	 * 
	 * @param source
	 * @param width
	 * @param target
	 * @return
	 */
	public static void width(String source, int width) {
		width(source, width, source);
	}

	public static void width(String source, int width, String target) {
		width(new File(source), width, new File(target));
	}

	public static void width(File source, int width, File target) {
		checkArgument((source.exists() && source.isFile()) && (target.exists() && target.isFile()),
				"The image file is not exist");
		int[] size = size(source);
		int w = size[0];
		int h = size[1];
		width = (width > w) ? w : width;
		int /* New height */ height = (width * h) / w;
		try {
			Thumbnails.of(source).size(width, height).toFile(target);
		} catch (IOException e) {
			LOG.error("Limited image width error!", e);
		}
	}

	/**
	 * Get the image size
	 * 
	 * @param path
	 *            the full image path
	 * @return {@code int} the size of the image
	 */
	public static int[] size(String path) {
		return size(new File(path));
	}

	public static int[] size(File file) {
		checkArgument(file.exists() && file.isFile(), "The image file is not exist");
		BufferedImage image = init(file);
		int[] result = new int[] { 0, 0 };
		if (image != null) {
			result[0] = image.getWidth();
			result[1] = image.getHeight();
		}
		return result;
	}

	// ------------private utilities-----------//
	private static BufferedImage init(File file) {
		checkArgument(file.exists() && file.isFile(), "The image file is not exist");
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			return null;
		}
	}
}

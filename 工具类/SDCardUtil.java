package com.qianfeng.day13_externalstorage_fileexplorer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;
import android.os.StatFs;

public class SDCardUtil {

	/**
	 * ����ⲿ�洢��״̬
	 */
	public static String getSDCardState() {
		String state = Environment.getExternalStorageState();
		return state;
	}
	
	/**
	 * ����ⲿ�洢�Ƿ���ȷ���أ����ɶ���д
	 */
	public static boolean isSDCardMounted() {
		String state = Environment.getExternalStorageState();
		return state.equals(Environment.MEDIA_MOUNTED);
	}
	
	/**
	 * ��ȡ�ⲿ�洢·��
	 */
	public static String getSDCardPath() {
		if (isSDCardMounted()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		}
		return null;
	}
	
	/**
	 * ��ȡ�ⲿ�洢���ܴ�С����λΪM
	 */
	public static long getSDCardSize() {
		if (isSDCardMounted()) {
			StatFs statFs = new StatFs(getSDCardPath());
			long blockSize = statFs.getBlockSize();
			long blockCount = statFs.getBlockCount();
			return blockSize * blockCount / 1024 / 1024;
		}
		return 0;
	}
	
	/**
	 * ��ȡ�ⲿ�洢�Ŀ��ô�С����λΪM
	 */
	public static long getSDCardAvailableSize() {
		if (isSDCardMounted()) {
			StatFs statFs = new StatFs(getSDCardPath());
			long blockSize = statFs.getBlockSize();
			long blockCount = statFs.getAvailableBlocks();
			return blockSize * blockCount / 1024 / 1024;
		}
		return 0;
	}
	
	/**
	 * ��ָ�����ݱ��浽ָ��λ�á�ָ���ļ������ļ���
	 * @param data Ҫ���������
	 * @param dir ���ļ������ƣ���Ҫ�ԡ�/����ͷ
	 * @param name �ļ���
	 * @return �ļ��Ƿ񱣴�ɹ�
	 */
	public static boolean write2File(byte[] data, String dir, String name) {
		if (isSDCardMounted()) {
			
			File dirFile = new File(getSDCardPath(), dir);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			
			BufferedOutputStream bufOutStream = null;
			try {
				File file = new File(dirFile, name);
				bufOutStream = new BufferedOutputStream(new FileOutputStream(file));
				bufOutStream.write(data);
				bufOutStream.flush();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (bufOutStream != null) {
					try {
						bufOutStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * ��ȡָ���ļ�
	 * @param path �ļ�·��
	 * @return �ļ����ֽ�����
	 */
	public static byte[] readFromFile(String path) {
		if (isSDCardMounted()) {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			
			BufferedInputStream inStream = null;
			try {
				inStream = new BufferedInputStream(new FileInputStream(path));
				byte[] temp = new byte[1024];
				int len = 0;
				while ((len = inStream.read(temp)) != -1) {
					outStream.write(temp, 0, len);
					outStream.flush();
				}
				return outStream.toByteArray();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (inStream != null) {
					try {
						inStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}
}

package com.franklin.logoutarmycd.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Serializable;

public class Serilizer {

	/**
	 * 將物件o進行序列化到檔案f中
	 * 
	 * @param o
	 * @param f
	 */
	static public void store(Serializable o, File f){
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(f));
			out.writeObject(o);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeOutputStream(out);
		}
	}
	
	/**
	 * 
	 * 
	 * @param out
	 */
	static private void closeOutputStream( ObjectOutputStream out ){
		if( out != null ){
			try {
				out.close();
			} catch (IOException e) {
				// ignore exception
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 從檔案f中反序列化出object
	 * 
	 * @param f
	 * @return
	 * @throws IOException 
	 */
	static public Object load(File f) throws IOException{
		ObjectInputStream in = null;
		Object resultObj = null;
		try {
			in = new ObjectInputStream(new FileInputStream(f));
			resultObj = in.readObject();
		} catch ( IOException e ){
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeInputStream(in);
		}
		return resultObj;
	}
	
	static private void closeInputStream( ObjectInputStream in ){
		if( in != null ){
			try {
				in.close();
			} catch (IOException e) {
				// ignore exception
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 深層複製, 物件所參考物件也進行序列化
	 * 
	 * @param o
	 * @return
	 */
	static Object deepclone(final Serializable o){
		Object resultObj = null;
		final PipedOutputStream pipeout = new PipedOutputStream();
		ObjectInputStream in = null;
		try {
			PipedInputStream pipein = new PipedInputStream(pipeout);
	
			Thread writer = new Thread(){
				public void run() {
					ObjectOutputStream out = null;
					try {
						out = new ObjectOutputStream(pipeout);
						out.writeObject(o);
					} catch (IOException e) {
						throw new RuntimeException(e);
					} finally {
						closeOutputStream(out);
					}
				};
			};
			writer.start();
			
			in = new ObjectInputStream(pipein);
			resultObj = in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeInputStream(in);
		}
		
		return resultObj;
	}

}

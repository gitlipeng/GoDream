package com.godream.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.godream.bean.DistrictInfo;

public class UtilTools {
	// 从本地读取json数据
	public static String readFile(Context context, String filename) {
		String str = null;
		try {
			InputStream inStream = context.getResources().getAssets()
					.open(filename);
			byte[] buffer = new byte[1024];
			int len = 0;
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			while ((len = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			inStream.close();
			outStream.close();

			str = outStream.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 将json字符串转换为DistrictInfo
	 * 
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static List<DistrictInfo> parserJson(String json) throws Exception {
		List<DistrictInfo> districs = new ArrayList<DistrictInfo>();
		JSONObject x1 = new JSONObject(json);
		String s1 = x1.get("liuzhichao.com").toString();
		JSONObject x2 = new JSONObject(s1);
		String s2 = x2.get("tableSet").toString();
		JSONObject x3 = new JSONObject(s2);
		JSONArray list = x3.getJSONArray("list");

		for (int i = 0; i < list.length(); i++) {
			DistrictInfo info = new DistrictInfo();
			JSONObject obj = list.getJSONObject(i);

			info.setId(obj.getString("projectId"));
			info.setName(obj.getString("name"));
			info.setAddress(obj.getString("address"));
			info.setLat(Double.parseDouble(obj.getString("lat")));
			info.setLng(Double.parseDouble(obj.getString("lng")));

			districs.add(info);
		}

		return districs;
	}

	public static byte[] getBytes(InputStream is) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[2048];
		int len = 0;
		try {
			while ((len = is.read(b, 0, 2048)) != -1) {
				baos.write(b, 0, len);
				baos.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] bytes = baos.toByteArray();
		return bytes;
	}

}

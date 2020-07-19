package me.Danker.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class APIHandler {
	public static JsonObject getResponse(String urlString, EntityPlayer player) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String input;
				StringBuffer response = new StringBuffer();
				
				while ((input = in.readLine()) != null) {
					response.append(input);
				}
				in.close();
				
				Gson gson = new Gson();
				JsonObject object = gson.fromJson(response.toString(), JsonObject.class);
				
				return object;
			} else {
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Request failed. Incorrect arguments?"));
			}
		} catch (MalformedURLException ex) {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "An error has occured. See logs for more details."));
			System.err.println(ex);
		} catch (IOException ex) {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "An error has occured. See logs for more details."));
			System.err.println(ex);
		}
		
		return new JsonObject();
	}
}

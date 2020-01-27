package com.ecommerce.rebanking.challenge.models.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.rebanking.challenge.models.dao.IPhoneDao;
import com.ecommerce.rebanking.challenge.models.entity.Phone;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

@Service
public class PhoneServiceImpl implements IPhoneService {

	@Autowired
	private IPhoneDao phoneDao;
	
	@Override
	@Transactional
	public Phone save(Phone phone) {
		return phoneDao.save(phone);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		phoneDao.delete(findById(id));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Phone> findAll() {
		return (List<Phone>) phoneDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Phone findById(Long id) {
		return phoneDao.findById(id).get();
	}

	@Override
	@Transactional
	public Phone findByDevice(String device) throws Exception {
		Phone phone = phoneDao.findByDevice(device);
		if(phone == null) {
			phone = findByDeviceInAPI(device);
			if(phone != null) {
				save(phone);
			}
			else {
				throw new Exception("Modelo de celular no encontrado. Asegurate de haberlo escrito correctamente.");
			}
		}
		return phone;
	}
	
	private Phone findByDeviceInAPI(String device) {
		Phone phone = null;
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
			.url("https://phonelabo.p.rapidapi.com/getdevice?version=Global&device="+device)
			.get()
			.addHeader("x-rapidapi-host", "phonelabo.p.rapidapi.com")
			.addHeader("x-rapidapi-key", "ac3728f626msh31e17f63df5fb32p1fc418jsn1d8addc86709")
			.build();
		try {
			Response response = client.newCall(request).execute();
			if(response.code() == 200) {
				ResponseBody responseBody = response.body();
				phone = requestToPhone(responseBody);
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return phone;
	}
	
	private Phone requestToPhone(ResponseBody responseBody) throws JsonSyntaxException, IOException {
		Gson gson = new Gson(); 
		Phone phone = gson.fromJson(responseBody.string(), Phone.class);
		return phone;
	}
	
	

}

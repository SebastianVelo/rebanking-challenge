package com.ecommerce.rebanking.challenge.models.services;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	private Logger logger = LogManager.getLogger(PhoneServiceImpl.class);

	@Override
	@Transactional
	public void delete(Phone id) {
		phoneDao.delete(findById(id));
	}
	
	@Override
	@Transactional
	public Phone save(Phone phone) {
		return phoneDao.save(phone);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Phone> findAll() {
		List<Phone> phones = phoneDao.findAll();
		logger.info("Response PhoneDao findAll" + (phones == null ? "Not found" : phones.toString()));
		return phones;
	}

	@Override
	@Transactional(readOnly = true)
	public Phone findById(Phone id) {
		Phone phone = phoneDao.findById(id).get();
		logger.info("Response PhoneDao findById: " + (phone == null ? "Not found" : phone.toString()));
		return phone;
	}

	@Override
	@Transactional
	public Phone findByDevice(String device) {
		Phone phone = phoneDao.findByDevice(device);	
		logger.info("Response PhoneDao findByDevice: " + (phone == null ? "Not found" : phone.toString()));
		if(phone == null) {
			phone = findByDeviceInAPI(device);
			if(phone != null) {
				phone = save(phone);
				logger.info("Response PhoneDao save: " + phone.toString());
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
		logger.info("Request getdevice: " + request.toString());
		try {
			Response response = client.newCall(request).execute();
			logger.info("Response getdevice: " + response.toString());
			if(response.code() == 200) {
				ResponseBody responseBody = response.body();
				phone = requestToPhone(responseBody);
			}			
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
				
		return phone;
	}
	
	private Phone requestToPhone(ResponseBody responseBody) {
		Gson gson = new Gson(); 
		Phone phone = null;
		try {
			phone = gson.fromJson(responseBody.string(), Phone.class);
		} catch (JsonSyntaxException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return phone;
	}
}

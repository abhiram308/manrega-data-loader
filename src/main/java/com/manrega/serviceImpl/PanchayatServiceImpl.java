package com.manrega.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.manrega.repository.PanchayatRepository;
import com.manrega.service.PanchayatService;

@Component
public class PanchayatServiceImpl implements PanchayatService {

	@Autowired
	private PanchayatRepository panchayatRepository;

	@Override
	public void addPanchayats(String url) {
		List<String> panchayats = null;
		try {
			Document doc = Jsoup.connect(url).get();
			Element musterRollTable = doc.select("tbody").get(2);

			Elements rows = musterRollTable.select("tr").next().next().next();
			long blockCode = Long.parseLong(url.split("block_code=")[1].split("&")[0]);
			
			for(Element element: rows) {
				String panchayatName = element.select("td").get(1).text();
				String issuedUrl = element.select("td").get(2).select("a").attr("href");
				long panchayatCode = Long.parseLong(issuedUrl.split("panchayat_code=")[1].split("&")[0]);
				
				String sql = "insert into panchayats (panchayat_code, panchayat_name, block_code) values" + " (" + panchayatCode
						+ ", '" + panchayatName + "'," + blockCode + ")";
				
				panchayatRepository.addPanchayat(sql);
			}
			//panchayats = new ArrayList<String>();

			//for (Element element : rows) {
			//	panchayats.add(element.select("td").get(1).text());
			//}
		} catch (Exception ex) {

		}
		//panchayatRepository.addPanchayat(panchayats);
	}

}

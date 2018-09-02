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
	public void addPanchayats() {
		List<String> panchayats = null;
		try {
			Document doc = Jsoup.connect(
					"http://mnregaweb4.nic.in/netnrega/state_html/emuster_wagelist_rpt.aspx?page=B&lflag=eng&state_name=MAHARASHTRA&state_code=18&district_name=BHANDARA&district_code=1828&block_name=MOHADI&block_code=1828027&fin_year=2018-2019&Digest=ZhvFKE7Zig/sCm5PlZFngw")
					.get();
			Element musterRollTable = doc.select("tbody").get(2);

			Elements rows = musterRollTable.select("tr").next().next().next();

//			http://mnregaweb4.nic.in/netnrega/state_html/emuster_wagelist_rpt.aspx?page=B&lflag=eng&state_name=MAHARASHTRA&state_code=18&district_name=BHANDARA&district_code=1828&block_name=MOHADI&block_code=1828027&fin_year=2018-2019&Digest=ZhvFKE7Zig/sCm5PlZFngw
			for(Element element: rows) {
				String panchayatName = element.select("td").get(1).text();
				String issuedUrl = element.select("td").get(2).select("a").attr("href");
				long panchayatCode = Long.parseLong(issuedUrl.split("panchayat_code=")[1].split("&")[0]);
				
			}
			panchayats = new ArrayList<String>();

			for (Element element : rows) {
				panchayats.add(element.select("td").get(1).text());
			}
		} catch (Exception ex) {

		}
		panchayatRepository.addPanchayats(panchayats);
	}

}

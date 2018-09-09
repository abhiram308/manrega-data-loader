package com.manrega.serviceImpl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.manrega.repository.DistrictRepository;
import com.manrega.service.BlockService;
import com.manrega.service.DistrictService;

@Component
public class DistrictServiceImpl implements DistrictService {
	@Autowired
	DistrictRepository districtRepository;
	@Autowired
	BlockService blockService;

	public void addDistricts(String url) throws Exception {
		// remove this when we pass url as argument
		url = "http://mnregaweb4.nic.in/netnrega/state_html/emuster_wagelist_rpt.aspx?page=S&lflag=eng&state_name=MAHARASHTRA&state_code=18&fin_year=2018-2019&source=national&Digest=R1Ic9jmPc3mN/xUu/jKSiQ";
		Document doc = Jsoup.connect(url).get();
		Element districtTable = doc.select("tbody").get(2);

		Elements rows = districtTable.select("tr").next().next().next();
		rows.remove(rows.size() - 1);
		long stateCode = Long.parseLong(url.split("state_code=")[1].split("&")[0]);
		for (Element element : rows) {
			Element district = element.select("td").get(1);
			String districtName = district.text();
			String districtUrl = district.select("a").attr("href");
			long districtCode = Long.parseLong(districtUrl.split("district_code=")[1].split("&")[0]);

			String sql = "insert into districts (district_code, district_name, state_code) values" + " (" + districtCode
					+ ", '" + districtName + "'," + stateCode + ")";

			districtRepository.addDistrict(sql);
			//Add blocks in that district
			blockService.addBlocks("http://mnregaweb4.nic.in/netnrega/state_html/" + districtUrl);
		}
	}

}

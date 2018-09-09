package com.manrega.serviceImpl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.manrega.repository.BlockRepository;
import com.manrega.service.BlockService;
import com.manrega.service.PanchayatService;

@Component
public class BlockServiceImpl implements BlockService {
	@Autowired
	BlockRepository blockRepository;
	@Autowired
	PanchayatService panchayatService;

	public void addBlocks(String url) throws Exception {
		// remove this when we pass url as argument
		//url = "http://mnregaweb4.nic.in/netnrega/state_html/emuster_wagelist_rpt.aspx?page=D&lflag=eng&state_name=MAHARASHTRA&state_code=18&district_name=BHANDARA&district_code=1828&fin_year=2018-2019&Digest=OgK7F7TH6gjWxoWDZAB4fA";
		Document doc = Jsoup.connect(url).get();
		Element blockTable = doc.select("tbody").get(2);

		Elements rows = blockTable.select("tr").next().next().next();
		rows.remove(rows.size() - 1);
		long districtCode = Long.parseLong(url.split("district_code=")[1].split("&")[0]);
		for (Element element : rows) {
			Element block = element.select("td").get(1);
			String blockName = block.text();
			String blockUrl = block.select("a").attr("href");
			long blockCode = Long.parseLong(blockUrl.split("block_code=")[1].split("&")[0]);
			String digest = blockUrl.split("Digest=")[1];

			String sql = "insert into blocks (block_code, block_name, district_code, digest) values" + " (" + blockCode
					+ ", '" + blockName + "'," + districtCode + ", '" + digest + "')";

			blockRepository.addBlock(sql);
			//Add panchayats in that block
			panchayatService.addPanchayats("http://mnregaweb4.nic.in/netnrega/state_html/" + blockUrl);
		}
	}
}
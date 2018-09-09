package com.manrega.service;

import org.springframework.stereotype.Service;

@Service
public interface BlockService {
	public void addBlocks(String url) throws Exception;
}
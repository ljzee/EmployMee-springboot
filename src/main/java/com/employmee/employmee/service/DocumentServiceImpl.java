package com.employmee.employmee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employmee.employmee.entity.Document;
import com.employmee.employmee.payload.request.UpdateDocumentRequest;
import com.employmee.employmee.repository.DocumentRepository;

@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	DocumentRepository documentRepository;
	
	@Override
	public void updateDocument(Document document, UpdateDocumentRequest updateDocumentRequest) {
		document.setName(updateDocumentRequest.getName());
		
		documentRepository.save(document);
	}

}

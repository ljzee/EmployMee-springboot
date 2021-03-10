package com.employmee.employmee.service;

import com.employmee.employmee.entity.Document;
import com.employmee.employmee.payload.request.UpdateDocumentRequest;

public interface DocumentService {
	public void updateDocument(Document document, UpdateDocumentRequest updateDocumentRequest);
}

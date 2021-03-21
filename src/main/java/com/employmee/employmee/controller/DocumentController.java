package com.employmee.employmee.controller;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.employmee.employmee.annotation.ValueOfEnum;
import com.employmee.employmee.entity.Document;
import com.employmee.employmee.entity.UserProfile;
import com.employmee.employmee.payload.request.UpdateDocumentRequest;
import com.employmee.employmee.payload.response.UserDocument;
import com.employmee.employmee.repository.DocumentRepository;
import com.employmee.employmee.repository.UserProfileRepository;
import com.employmee.employmee.security.MyUserDetails;
import com.employmee.employmee.service.DocumentService;
import com.employmee.employmee.service.StorageService;
import com.employmee.employmee.service.UserService;

@Validated
@RestController
@RequestMapping("/document")
public class DocumentController {
	
	@Autowired
	DocumentService documentService;
	
	@Autowired
	StorageService storageService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	DocumentRepository documentRepository;
	
	@Autowired
	UserProfileRepository userProfileRepository;
	
	@GetMapping("")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getUserDocuments() {
		UserProfile userProfile = this.getCurrentUserProfile();
		
		Set<Document> documents = userProfile.getDocuments();
		Iterator<Document> iterator = documents.iterator();
		List<UserDocument> userDocuments = new ArrayList<>();
		while(iterator.hasNext()) {
			Document document = iterator.next();
			
			userDocuments.add(new UserDocument(document));
		}
		
		return ResponseEntity.ok(userDocuments);
	}
	
	@PostMapping("")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> uploadDocument(@RequestParam MultipartFile file, 
											@RequestParam @ValueOfEnum(enumClass = Document.TYPE.class, message = "Document must be one of RESUME, COVERLETTER, or OTHER.") String type, 
											@RequestParam String name) {
		
		Path filePath = storageService.store(file);
		
		UserProfile userProfile = this.getCurrentUserProfile();
		userService.addDocument(userProfile, filePath, file, type, name);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{documentId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> serveDocument(@PathVariable int documentId) {
		Optional<Document> documentOptional = documentRepository.findById(documentId);
		if(documentOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Document document = documentOptional.get();
		
		// check that document belongs to the user requesting the document
		UserProfile userProfile = this.getCurrentUserProfile();
		if(!userProfile.hasDocument(document)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		Resource file = storageService.loadAsResource(FilenameUtils.getName(document.getPath()));
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getFileName() + "\"").body(file);
	}
	
	@PutMapping("/{documentId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> updateDocument(@PathVariable int documentId, @Valid @RequestBody UpdateDocumentRequest updateDocumentRequest) {
		Optional<Document> documentOptional = documentRepository.findById(documentId);
		if(documentOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Document document = documentOptional.get();
		UserProfile userProfile = this.getCurrentUserProfile();
		if(!userProfile.hasDocument(document)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		documentService.updateDocument(document, updateDocumentRequest);
		
		return ResponseEntity.ok().build();
	}
	
	private UserProfile getCurrentUserProfile() {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Optional<UserProfile> result = userProfileRepository.findById(userDetails.getId());
		return result.get();
	}
	
	
}

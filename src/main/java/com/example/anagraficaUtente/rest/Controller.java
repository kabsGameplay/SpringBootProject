package com.example.anagraficaUtente.rest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.anagraficaUtente.bean.Anagrafica;
import com.example.anagraficaUtente.database.AnaDB;

@RestController
public class Controller {
	AnaDB a = new AnaDB();

	@PostMapping("/insertdb")
	public String insertana(@RequestBody Anagrafica anag) throws IOException {
		a.scrivoAnagraficaSuFile(anag);
		a.insertDb(anag);
		return "success";
	}

	@GetMapping("/readdb")
	public List<Anagrafica> getDatalist() {
		List<Anagrafica> lista = a.getAnaData();
		return lista;
	}

	@GetMapping("/leggi-file")
	public ResponseEntity<String> leggiDati() {

		try {

			// CREO UN OGGETTO BUFFEREDREDADER PER LEGGERE IL CONTENUTO DEL FILE
			BufferedReader reader = new BufferedReader(new FileReader("exercise.txt"));

			// LEGGO IL CONTENUTO DEL FILE E LO METTO IN UNA STRINGA
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}

			// CHIUDO L'OGGETTO PER LEGGERE IL FILE
			reader.close();

			// RESTITUISCO IL CONTENUTO DEL FILE COME RESPONSE HTTP
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.TEXT_PLAIN);
			return new ResponseEntity<String>(sb.toString(), headers, HttpStatus.OK);

		} catch (IOException e) {
			e.printStackTrace();
			// IN CASO DI ERRORE RESTITUISCO UN MESSAGGIO DI ERRORE CON UNO STATO HTTP 500
			return new ResponseEntity<String>("Errore durante la lettura del file", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
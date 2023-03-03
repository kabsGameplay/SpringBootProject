package com.example.anagraficaUtente.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.anagraficaUtente.bean.Anagrafica;

public class AnaDB {
	Database db = new Database();

	public void insertDb(Anagrafica k) {
		Connection conn = null;
		try {
			conn = db.getConnection();
			PreparedStatement statement = conn.prepareStatement(
					"Insert into anagrafica(nome,cognome,email,telefono,CF,indirizzo) values (?,?,?,?,?,?)");
			String nome = k.getNome();
			String cognome = k.getCognome();
			String email = k.getEmail();
			String telefono = k.getTelefono();
			String cf = k.getCf();
			String indirizzo = k.getIndirizzo();

			statement.setString(1, nome);
			statement.setString(2, cognome);
			statement.setString(3, email);
			statement.setString(4, telefono);
			statement.setString(5, cf);
			statement.setString(6, indirizzo);

			boolean f = statement.execute();

			if (f == false) {
				System.out.println("Insertion successful!");
			}

			else {
				System.out.println("Insertion unsuccessful!");
			}

			System.out.println();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Anagrafica> getAnaData() {

		// CREO L'OGGETTO CONNESSIONE
		Connection dbconn = null;

		List<Anagrafica> lista = new ArrayList<Anagrafica>();

		try {

			// VALORIZZO L'OGGETTO CONNESSIONE
			dbconn = db.getConnection();

			// PREPARO LA QUERY
			PreparedStatement statement = dbconn.prepareStatement("SELECT * FROM anagrafica");

			// LANCIO LA QUERY SUL DATABASE E I RISULTATI ME LI RESTITUSCIE IN UN OGGETTO DI
			// TIPO RESULTSET
			ResultSet rs = statement.executeQuery();

			// CICLO I VALORI CHE ARRIVANO DAL DB
			while (rs.next()) {

				Anagrafica ana = new Anagrafica();

				// INSERISCO IL NOME DELLA COLONNA E MI PRENDO IL VALORE
				ana.setNome(rs.getString("nome"));
				ana.setCognome(rs.getString("cognome"));
				ana.setId(rs.getInt("id"));
				ana.setTelefono(rs.getString("telefono"));
				ana.setIndirizzo(rs.getString("indirizzo"));
				ana.setCf(rs.getString("cf"));
				ana.setEmail(rs.getString("email"));

				lista.add(ana);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				dbconn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return lista;

	}

	public void scrivoAnagraficaSuFile(Anagrafica anag) throws IOException {
		// LIBRERIA FILE PER CREARE UN NUOVO FILE
		File file = new File("exercise.txt");

		// USO LA LIBRERIA FILEWRITER PER INSERIRE DATI SUL FILE DI TESTO
		FileWriter fwriter = new FileWriter(file, true);
		int lungnome = 100;
		String nome = anag.getNome();
		int diflungnome = lungnome - nome.length();
		String spazio = " ";

		for (int i = 0; i < diflungnome; i++) {
			nome += spazio.replace(" ", "-");
		}

		int lungcognome = 100;
		String cognome = anag.getCognome();
		int diflungcognome = lungcognome - cognome.length();

		for (int i = 0; i < diflungcognome; i++) {
			cognome += spazio.replace(" ", "-");
		}

		int lungtelefono = 50;
		String telefono = anag.getTelefono();
		int diflungtelefono = lungtelefono - telefono.length();

		for (int i = 0; i < diflungtelefono; i++) {
			telefono += spazio.replace(" ", "-");
		}

		int lungindirizzo = 255;
		String indirizzo = anag.getIndirizzo();
		int diflungindirizzo = lungindirizzo - indirizzo.length();

		for (int i = 0; i < diflungindirizzo; i++) {
			indirizzo += spazio.replace(" ", "-");
		}

		int lungcodicefiscale = 50;
		String codicefiscale = anag.getCf();
		int diflungcodicefiscale = lungcodicefiscale - codicefiscale.length();

		for (int i = 0; i < diflungcodicefiscale; i++) {
			codicefiscale += spazio.replace(" ", "-");
		}

		int lungemail = 50;
		String email = anag.getEmail();
		int diflungemail = lungemail - email.length();

		for (int i = 0; i < diflungemail; i++) {
			email += spazio.replace(" ", "-");
		}

		try {

			fwriter.write(anag.getId() + " ,");
			fwriter.write(nome + " ,");
			fwriter.write(cognome + " ,");
			fwriter.write(telefono + " ,");
			fwriter.write(indirizzo + " ,");
			fwriter.write(codicefiscale + " ,");
			fwriter.write(email + "\n");

		} finally {
			try {
				fwriter.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Errore nel metodo scrivoAnagraficaSuFile " + e.getMessage());
			}
		}

	}
}

package no.hvl.dat100.oppgave5;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import no.hvl.dat100.oppgave2.Bilde;
import no.hvl.dat100.oppgave2.Tekst;
import no.hvl.dat100.oppgave3.Blogg;

public class LesBlogg {

	private static final String TEKST = "TEKST";
	private static final String BILDE = "BILDE";

	public static Blogg les(String mappe, String filnavn) {
		Path fil = Path.of(mappe, filnavn);
		try (BufferedReader leser = Files.newBufferedReader(fil, StandardCharsets.UTF_8)) {
			String antallLinje = leser.readLine();
			if (antallLinje == null) {
				throw new IOException("Filen mangler antall innlegg");
			}
			int antall = Integer.parseInt(antallLinje.trim());
			if (antall < 0) {
				throw new IOException("Antall innlegg kan ikke være negativt");
			}

			Blogg samling = new Blogg(antall);
			for (int i = 0; i < antall; i++) {
				String type = lesLinje(leser, "innleggstype");
				int id = Integer.parseInt(lesLinje(leser, "id").trim());
				String bruker = lesLinje(leser, "bruker").stripTrailing();
				String dato = lesLinje(leser, "dato").stripTrailing();
				int likes = Integer.parseInt(lesLinje(leser, "likes").trim());
				String tekst = lesLinje(leser, "tekst");

				if (TEKST.equals(type)) {
					samling.leggTil(new Tekst(id, bruker, dato, likes, tekst));
				} else if (BILDE.equals(type)) {
					String url = lesLinje(leser, "URL");
					samling.leggTil(new Bilde(id, bruker, dato, likes, tekst, url));
				} else {
					throw new IOException("Ukjent innleggstype: " + type);
				}
			}
			return samling;
		} catch (IOException | NumberFormatException e) {
			System.err.println("Kunne ikke lese blogg fra " + fil + ": " + e.getMessage());
			return null;
		}
	}

	private static String lesLinje(BufferedReader leser, String felt) throws IOException {
		String linje = leser.readLine();
		if (linje == null) {
			throw new IOException("Filen sluttet før feltet " + felt);
		}
		return linje;
	}
}

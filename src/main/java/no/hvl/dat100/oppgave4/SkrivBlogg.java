package no.hvl.dat100.oppgave4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import no.hvl.dat100.oppgave3.Blogg;

public class SkrivBlogg {

	public static boolean skriv(Blogg samling, String mappe, String filnavn) {
		Path fil = Path.of(mappe, filnavn);
		try {
			Files.writeString(fil, samling.toString(), StandardCharsets.UTF_8);
			return true;
		} catch (IOException e) {
			System.err.println("Kunne ikke skrive blogg til " + fil + ": " + e.getMessage());
			return false;
		}
	}
}

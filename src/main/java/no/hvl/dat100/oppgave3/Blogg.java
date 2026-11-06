package no.hvl.dat100.oppgave3;

import no.hvl.dat100.oppgave1.Innlegg;
import no.hvl.dat100.oppgave2.Tekst;

import java.util.Arrays;
import java.util.Objects;

public class Blogg {

	private Innlegg[] innleggtabell;
	private int nesteledig;

	public Blogg() {
		this(20);
	}

	public Blogg(int lengde) {
		innleggtabell = new Innlegg[lengde];
		nesteledig = 0;
	}

	public int getAntall() {
		return nesteledig;
	}
	
	public Innlegg[] getSamling() {
		return innleggtabell;
	}
	
	public int finnInnlegg(Innlegg innlegg) {
		for (int i = 0; i < nesteledig; i++) {
			if (innleggtabell[i].erLik(innlegg)) {
				return i;
			}
		}
		return -1;
	}

	public boolean finnes(Innlegg innlegg) {
		return finnInnlegg(innlegg) >= 0;
	}

	public boolean ledigPlass() {
		return nesteledig < innleggtabell.length;
	}
	
	public boolean leggTil(Innlegg innlegg) {
		if (innlegg == null || !ledigPlass() || finnes(innlegg)) {
			return false;
		}
		innleggtabell[nesteledig] = innlegg;
		nesteledig++;
		return true;
	}
	
	public String toString() {
		StringBuilder resultat = new StringBuilder().append(nesteledig).append("\n");
		for (int i = 0; i < nesteledig; i++) {
			resultat.append(innleggtabell[i].toString());
		}
		return resultat.toString();
	}

	// valgfrie oppgaver nedenfor
	
	public void utvid() {
		int nyLengde = innleggtabell.length == 0 ? 1 : innleggtabell.length * 2;
		innleggtabell = Arrays.copyOf(innleggtabell, nyLengde);
	}
	
	public boolean leggTilUtvid(Innlegg innlegg) {
		if (innlegg == null || finnes(innlegg)) {
			return false;
		}
		if (!ledigPlass()) {
			utvid();
		}
		innleggtabell[nesteledig++] = innlegg;
		return true;
	}
	
	public boolean slett(Innlegg innlegg) {
		int indeks = finnInnlegg(innlegg);
		if (indeks < 0) {
			return false;
		}
		for (int i = indeks; i < nesteledig - 1; i++) {
			innleggtabell[i] = innleggtabell[i + 1];
		}
		innleggtabell[--nesteledig] = null;
		return true;
	}
	
	public int[] search(String keyword) {
		return finnTekstTreff(null, keyword, false);
	}

	public int[] search(String user, String ord) {
		return finnTekstTreff(user, ord, true);
	}

	private int[] finnTekstTreff(String user, String ord, boolean filtrerBruker) {
		if (ord == null) {
			return new int[0];
		}
		int[] treff = new int[nesteledig];
		int antallTreff = 0;
		for (int i = 0; i < nesteledig; i++) {
			Innlegg innlegg = innleggtabell[i];
			if (innlegg instanceof Tekst tekstInnlegg
					&& (!filtrerBruker || Objects.equals(user, innlegg.getBruker()))
					&& tekstInnlegg.getTekst() != null
					&& tekstInnlegg.getTekst().contains(ord)) {
				treff[antallTreff++] = innlegg.getId();
			}
		}
		return Arrays.copyOf(treff, antallTreff);
	}
}
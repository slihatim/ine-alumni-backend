package com.ine.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ine.backend.entities.Evenement;
import com.ine.backend.repositories.EvenementRepository;

@Service
public class EvenementService {

	private final EvenementRepository evenementRepository;

	public EvenementService(EvenementRepository evenementRepository) {
		this.evenementRepository = evenementRepository;
	}

	public Evenement ajouterEvenement(Evenement evenement) {
		return evenementRepository.save(evenement);
	}

	public List<Evenement> getTousLesEvenements() {
		return evenementRepository.findAll();
	}

	public Optional<Evenement> getEvenementParId(Long id) {
		return evenementRepository.findById(id);
	}

	public void supprimerEvenement(Long id) {
		evenementRepository.deleteById(id);
	}
}

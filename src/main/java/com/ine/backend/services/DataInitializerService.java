package com.ine.backend.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ine.backend.entities.*;
import com.ine.backend.repositories.*;

@Service
public class DataInitializerService implements CommandLineRunner {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private LaureatRepository laureatRepository;

	@Autowired
	private EducationRepository educationRepository;

	@Autowired
	private ExperienceRepository experienceRepository;

	@Autowired
	private SkillRepository skillRepository;

	@Autowired
	private ExternalLinkRepository externalLinkRepository;

	@Autowired
	private CompanyReviewRepository companyReviewRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private final Random random = new Random();

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		if (laureatRepository.count() == 0) {

			List<Company> companies = createCompanies();
			List<Laureat> alumni = createAlumni(companies);
			createEducations(alumni);
			createExperiences(alumni, companies);
			createSkills(alumni);
			createExternalLinks(alumni, companies);
			createCompanyReviews(alumni, companies);

		}
	}

	private List<Company> createCompanies() {
		List<Company> companies = new ArrayList<>();

		String[][] companyData = {
				{"OCP Group", "Mining & Chemicals", "Casablanca, Morocco", "Leading phosphate producer worldwide"},
				{"Attijariwafa Bank", "Banking & Financial Services", "Casablanca, Morocco",
						"Premier banking group in Morocco"},
				{"BMCE Bank", "Banking & Financial Services", "Rabat, Morocco",
						"International banking and financial services"},
				{"Maroc Telecom", "Telecommunications", "Rabat, Morocco", "Leading telecom operator in Morocco"},
				{"ONCF", "Transportation", "Rabat, Morocco", "National railway company of Morocco"},
				{"Microsoft Morocco", "Technology", "Casablanca, Morocco", "Global technology leader"},
				{"IBM Morocco", "Technology", "Casablanca, Morocco", "Enterprise technology and consulting"},
				{"Capgemini Morocco", "IT Consulting", "Rabat, Morocco", "Global consulting and technology services"},
				{"Accenture Morocco", "IT Consulting", "Casablanca, Morocco",
						"Strategy, consulting, digital, technology"},
				{"Orange Morocco", "Telecommunications", "Casablanca, Morocco", "Mobile and internet services"},
				{"Inwi", "Telecommunications", "Rabat, Morocco", "Mobile network operator"},
				{"BMCI", "Banking & Financial Services", "Casablanca, Morocco", "Commercial and investment banking"},
				{"CDG Group", "Financial Services", "Rabat, Morocco", "Development and investment institution"},
				{"Société Générale Maroc", "Banking", "Casablanca, Morocco", "International banking services"},
				{"Dell Technologies Morocco", "Technology", "Rabat, Morocco", "Enterprise technology solutions"},
				{"Deloitte Morocco", "Consulting", "Casablanca, Morocco", "Professional services firm"},
				{"PwC Morocco", "Consulting", "Rabat, Morocco", "Audit, tax and advisory services"},
				{"KPMG Morocco", "Consulting", "Casablanca, Morocco", "Audit, tax and advisory services"},
				{"EY Morocco", "Consulting", "Rabat, Morocco", "Professional services in assurance, tax"},
				{"Sopra Steria Morocco", "IT Services", "Rabat, Morocco", "Digital transformation services"}};

		for (String[] data : companyData) {
			Company company = new Company();
			company.setName(data[0]);
			company.setIndustry(data[1]);
			company.setLocation(data[2]);
			company.setDescription(data[3]);
			company.setWebsite("https://www." + data[0].toLowerCase().replace(" ", "").replace("&", "") + ".com");
			company.setLogo(data[0].toLowerCase().replace(" ", "-") + "-logo.png");

			// Add HR contact info
			company.setHrContactName(generateRandomName());
			company.setHrContactEmail("hr@" + data[0].toLowerCase().replace(" ", "").replace("&", "") + ".com");
			company.setHrContactPhone("+212" + (600000000 + random.nextInt(99999999)));

			companies.add(companyRepository.save(company));
		}

		return companies;
	}

	private List<Laureat> createAlumni(List<Company> companies) {
		List<Laureat> alumni = new ArrayList<>();

		String[][] alumniData = {{"Yassine Errakka", "ASEDS", "2021", "Rabat", "Morocco", "Senior Software Engineer"},
				{"Fatima Zahra Benali", "DATA", "2020", "Casablanca", "Morocco", "Data Scientist"},
				{"Mohammed Alami", "CYBER_SECURITY", "2019", "Rabat", "Morocco", "Cybersecurity Specialist"},
				{"Aicha Bennani", "CLOUD", "2022", "Casablanca", "Morocco", "Cloud Architect"},
				{"Omar Tazi", "AMOA", "2018", "Rabat", "Morocco", "Business Analyst"},
				{"Nadia Fassi", "ASEDS", "2021", "Casablanca", "Morocco", "Full Stack Developer"},
				{"Hamza Berrada", "DATA", "2020", "Rabat", "Morocco", "Machine Learning Engineer"},
				{"Salma Idrissi", "SMART", "2019", "Casablanca", "Morocco", "IoT Solutions Architect"},
				{"Khalid Amrani", "CYBER_SECURITY", "2018", "Rabat", "Morocco", "Security Consultant"},
				{"Rim Benkirane", "CLOUD", "2022", "Casablanca", "Morocco", "DevOps Engineer"},
				{"Mehdi Lahlou", "ASEDS", "2017", "Rabat", "Morocco", "Technical Lead"},
				{"Zineb Mekouar", "DATA", "2021", "Casablanca", "Morocco", "Data Analyst"},
				{"Saad Benjelloun", "AMOA", "2019", "Rabat", "Morocco", "Product Manager"},
				{"Karima Bennis", "SMART", "2020", "Casablanca", "Morocco", "Systems Engineer"},
				{"Rachid Alaoui", "CYBER_SECURITY", "2018", "Rabat", "Morocco", "Penetration Tester"},
				{"Leila Chraibi", "CLOUD", "2021", "Casablanca", "Morocco", "Cloud Solutions Engineer"},
				{"Tarik Bendaoud", "ASEDS", "2020", "Rabat", "Morocco", "Software Architect"},
				{"Amina Kettani", "DATA", "2019", "Casablanca", "Morocco", "Business Intelligence Developer"},
				{"Hicham Benali", "AMOA", "2017", "Rabat", "Morocco", "IT Project Manager"},
				{"Sara Lamrini", "SMART", "2022", "Casablanca", "Morocco", "Embedded Systems Engineer"},
				{"Bilal Tounsi", "CYBER_SECURITY", "2020", "Rabat", "Morocco", "Information Security Officer"},
				{"Ghita Benjelloun", "CLOUD", "2019", "Casablanca", "Morocco", "Infrastructure Engineer"},
				{"Amine Rachidi", "ASEDS", "2018", "Rabat", "Morocco", "Senior Developer"},
				{"Nouhaila Berrada", "DATA", "2021", "Casablanca", "Morocco", "Data Engineer"},
				{"Karim Benkirane", "AMOA", "2020", "Rabat", "Morocco", "Systems Analyst"},
				{"Imane Alami", "SMART", "2019", "Casablanca", "Morocco", "R&D Engineer"},
				{"Youssef Tazi", "CYBER_SECURITY", "2018", "Rabat", "Morocco", "Security Architect"},
				{"Laila Fassi", "CLOUD", "2021", "Casablanca", "Morocco", "Platform Engineer"},
				{"Hassan Idrissi", "ASEDS", "2017", "Rabat", "Morocco", "Tech Lead"},
				{"Meryem Bennis", "DATA", "2020", "Casablanca", "Morocco", "Research Scientist"}};

		for (String[] data : alumniData) {
			Laureat laureat = new Laureat();

			// Basic info
			laureat.setFullName(data[0]);
			laureat.setMajor(Major.valueOf(data[1]));
			laureat.setGraduationYear(Integer.parseInt(data[2]));
			laureat.setCity(data[3]);
			laureat.setCountry(data[4]);
			laureat.setCurrentPosition(data[5]);

			// User info
			laureat.setEmail(generateEmail(data[0]));
			laureat.setPassword(passwordEncoder.encode("password123"));
			laureat.setRole(Role.ROLE_USER);
			laureat.setIsAccountVerified(true);

			// Additional info
			laureat.setPhoneNumber("+212" + (600000000 + random.nextInt(99999999)));
			laureat.setBirthDate(
					LocalDate.of(1995 + random.nextInt(8), 1 + random.nextInt(12), 1 + random.nextInt(28)));
			laureat.setGender(random.nextBoolean() ? Gender.MALE : Gender.FEMALE);
			laureat.setLinkedinId(generateLinkedinId(data[0]));
			laureat.setBio(generateBio(data[5], data[1]));
			laureat.setProfilePicture(generateProfilePicture(data[0]));

			// Assign random company
			laureat.setCurrentCompany(companies.get(random.nextInt(companies.size())));

			alumni.add(laureatRepository.save(laureat));
		}

		return alumni;
	}

	private void createEducations(List<Laureat> alumni) {
		String[] institutions = {"Institut National des Postes et Télécommunications (INPT)",
				"École Nationale Supérieure d'Informatique et d'Analyse des Systèmes (ENSIAS)",
				"École Mohammadia d'Ingénieurs (EMI)", "École Nationale Supérieure des Mines de Rabat"};

		String[] degrees = {"Ingénieur d'État", "Master en Informatique", "Master en Télécommunications",
				"Master en Réseaux et Systèmes"};

		for (Laureat laureat : alumni) {
			// INPT Education (main degree) - ensure years are within valid range
			Education inptEducation = new Education();
			inptEducation.setLaureat(laureat);
			inptEducation.setInstitutionName("Institut National des Postes et Télécommunications (INPT)");
			inptEducation.setDegree("Ingénieur d'État");
			inptEducation.setFieldOfStudy(getMajorDescription(laureat.getMajor()));

			// Ensure start year is valid (< 2025 and reasonable)
			int startYear = Math.min(laureat.getGraduationYear() - 3, 2021); // Max start in 2021
			int endYear = Math.min(laureat.getGraduationYear(), 2024); // Max end in 2024

			inptEducation.setStartYear(Math.max(startYear, 2015)); // Minimum start in 2015
			inptEducation.setEndYear(endYear);
			inptEducation.setDescription("Specialized in " + getMajorDescription(laureat.getMajor())
					+ " with focus on practical applications and industry collaboration.");
			educationRepository.save(inptEducation);

			// Some alumni have additional education
			if (random.nextFloat() < 0.3 && laureat.getGraduationYear() < 2023) { // Only for earlier graduates
				Education additionalEducation = new Education();
				additionalEducation.setLaureat(laureat);
				additionalEducation.setInstitutionName(institutions[random.nextInt(institutions.length)]);
				additionalEducation.setDegree(degrees[random.nextInt(degrees.length)]);
				additionalEducation.setFieldOfStudy("Advanced " + getMajorDescription(laureat.getMajor()));

				// Ensure additional education years are valid
				int addStartYear = Math.min(laureat.getGraduationYear() + 1, 2022);
				int addEndYear = Math.min(laureat.getGraduationYear() + 2, 2024);

				additionalEducation.setStartYear(addStartYear);
				additionalEducation.setEndYear(addEndYear);
				additionalEducation.setDescription("Advanced studies in specialized domain");
				educationRepository.save(additionalEducation);
			}
		}
	}

	private void createExperiences(List<Laureat> alumni, List<Company> companies) {
		for (Laureat laureat : alumni) {
			int numExperiences = 1 + random.nextInt(3); // 1-3 experiences
			int startYear = Math.max(laureat.getGraduationYear(), 2018); // Ensure reasonable start year

			// Make sure we don't exceed 2024 (constraint is < 2025)
			if (startYear >= 2025) {
				startYear = 2024;
			}

			for (int i = 0; i < numExperiences; i++) {
				Experience experience = new Experience();
				experience.setLaureat(laureat);
				experience.setCompany(companies.get(random.nextInt(companies.size())));

				if (i == numExperiences - 1) {
					// Current position - make sure start year is valid
					experience.setJobTitle(laureat.getCurrentPosition());
					experience.setStartYear(Math.min(startYear, 2024)); // Ensure < 2025
					experience.setEndYear(null);
					experience.setCompany(laureat.getCurrentCompany());
				} else {
					// Previous position
					experience.setJobTitle(generatePreviousPosition(laureat.getCurrentPosition()));
					experience.setStartYear(Math.min(startYear, 2024)); // Ensure < 2025
					int duration = 1 + random.nextInt(3); // 1-3 years
					int endYear = Math.min(startYear + duration, 2024); // Ensure reasonable end year
					experience.setEndYear(endYear);
					startYear = endYear; // Next experience starts after this one ends
				}

				experience.setLocation(experience.getCompany().getLocation());
				experience.setDomain(getDomainForMajor(laureat.getMajor()));
				experience.setDescription(generateExperienceDescription(experience.getJobTitle()));

				experienceRepository.save(experience);
			}
		}
	}

	private void createSkills(List<Laureat> alumni) {
		String[][] skillsByCategory = {
				// PROGRAMMING_LANGUAGES
				{"Java", "Python", "JavaScript", "TypeScript", "C++", "C#", "Go", "Rust", "Scala", "Kotlin"},
				// FRAMEWORKS_LIBRARIES
				{"Spring Boot", "React", "Angular", "Vue.js", "Django", "Flask", "Node.js", "Express.js", "Hibernate",
						"JPA"},
				// DATABASES
				{"PostgreSQL", "MySQL", "MongoDB", "Redis", "Oracle", "ElasticSearch", "Cassandra", "Neo4j"},
				// CLOUD_PLATFORMS
				{"AWS", "Microsoft Azure", "Google Cloud", "Docker", "Kubernetes", "OpenShift"},
				// TOOLS_SOFTWARE
				{"Git", "Jenkins", "Maven", "Gradle", "IntelliJ IDEA", "Visual Studio Code", "Postman", "Swagger"},
				// METHODOLOGIES
				{"Agile", "Scrum", "DevOps", "CI/CD", "TDD", "Microservices", "REST APIs", "GraphQL"}};

		SkillCategory[] categories = SkillCategory.values();

		for (Laureat laureat : alumni) {
			int numSkills = 8 + random.nextInt(8); // 8-15 skills per person

			for (int i = 0; i < numSkills; i++) {
				int categoryIndex = random.nextInt(skillsByCategory.length);
				String[] categorySkills = skillsByCategory[categoryIndex];

				Skill skill = new Skill();
				skill.setLaureat(laureat);
				skill.setName(categorySkills[random.nextInt(categorySkills.length)]);
				skill.setCategory(categories[categoryIndex]);

				skillRepository.save(skill);
			}
		}
	}

	private void createExternalLinks(List<Laureat> alumni, List<Company> companies) {
		// Alumni external links
		for (Laureat laureat : alumni) {
			// LinkedIn (most have this)
			if (random.nextFloat() < 0.9) {
				ExternalLink linkedin = new ExternalLink();
				linkedin.setLaureat(laureat);
				linkedin.setTitle("LinkedIn Profile");
				linkedin.setUrl("https://linkedin.com/in/" + laureat.getLinkedinId());
				linkedin.setLinkType(LinkType.LINKEDIN);
				externalLinkRepository.save(linkedin);
			}

			// Portfolio (some have this)
			if (random.nextFloat() < 0.4) {
				ExternalLink portfolio = new ExternalLink();
				portfolio.setLaureat(laureat);
				portfolio.setTitle("Portfolio Website");
				portfolio.setUrl("https://portfolio-" + laureat.getLinkedinId() + ".com");
				portfolio.setLinkType(LinkType.PORTFOLIO);
				externalLinkRepository.save(portfolio);
			}

			// GitHub (developers have this)
			if (random.nextFloat() < 0.6) {
				ExternalLink github = new ExternalLink();
				github.setLaureat(laureat);
				github.setTitle("GitHub Profile");
				github.setUrl("https://github.com/" + laureat.getLinkedinId());
				github.setLinkType(LinkType.GITHUB);
				externalLinkRepository.save(github);
			}
		}

		// Company external links
		for (Company company : companies) {
			ExternalLink website = new ExternalLink();
			website.setCompany(company);
			website.setTitle("Company Website");
			website.setUrl(company.getWebsite());
			website.setLinkType(LinkType.WEBSITE);
			externalLinkRepository.save(website);
		}
	}

	private void createCompanyReviews(List<Laureat> alumni, List<Company> companies) {
		String[] reviewComments = {
				"Great company culture with excellent opportunities for professional growth. The team is very collaborative and supportive.",
				"Innovative projects and cutting-edge technology. Management is understanding and promotes work-life balance.",
				"Challenging work environment that pushes you to grow. Good compensation and benefits package.",
				"Amazing learning opportunities and mentorship programs. The company truly invests in employee development.",
				"Dynamic and fast-paced environment. Great place to start your career and gain valuable experience.",
				"Strong technical leadership and well-structured development processes. Highly recommended for engineers.",
				"Excellent work environment with modern tools and technologies. The team is passionate about quality.",
				"Good career progression opportunities and competitive salary. Company values innovation and creativity.",
				"Collaborative culture with emphasis on continuous learning. Management is open to new ideas and feedback.",
				"Professional development is encouraged and supported. Great place to work with talented professionals."};

		// Create reviews (about 60% of alumni review companies)
		for (Laureat laureat : alumni) {
			if (random.nextFloat() < 0.6) {
				// Review current company
				CompanyReview review = new CompanyReview();
				review.setCompany(laureat.getCurrentCompany());
				review.setLaureat(laureat);
				review.setRating(3 + random.nextInt(3)); // 3-5 stars (mostly positive)
				review.setComment(reviewComments[random.nextInt(reviewComments.length)]);
				companyReviewRepository.save(review);
			}

			// Some alumni review additional companies they worked for
			if (random.nextFloat() < 0.3) {
				Company randomCompany = companies.get(random.nextInt(companies.size()));
				if (!randomCompany.equals(laureat.getCurrentCompany())) {
					CompanyReview additionalReview = new CompanyReview();
					additionalReview.setCompany(randomCompany);
					additionalReview.setLaureat(laureat);
					additionalReview.setRating(2 + random.nextInt(4)); // 2-5 stars (mixed reviews)
					additionalReview.setComment(reviewComments[random.nextInt(reviewComments.length)]);
					companyReviewRepository.save(additionalReview);
				}
			}
		}
	}

	// Helper methods
	private String generateRandomName() {
		String[] firstNames = {"Ahmed", "Fatima", "Mohamed", "Aicha", "Omar", "Khadija", "Youssef", "Nadia"};
		String[] lastNames = {"Alami", "Benali", "Idrissi", "Tazi", "Bennani", "Fassi", "Berrada", "Amrani"};
		return firstNames[random.nextInt(firstNames.length)] + " " + lastNames[random.nextInt(lastNames.length)];
	}

	private String generateEmail(String fullName) {
		return fullName.toLowerCase().replace(" ", ".") + "@gmail.com";
	}

	private String generateLinkedinId(String fullName) {
		return fullName.toLowerCase().replace(" ", "-");
	}

	private String generateProfilePicture(String fullName) {
		return fullName.toLowerCase().replace(" ", "-") + "-profile.jpg";
	}

	private String generateBio(String position, String major) {
		return String.format(
				"%s with expertise in %s. Passionate about technology innovation and continuous learning. "
						+ "Graduate of INPT with strong background in %s and practical industry experience.",
				position, getMajorDescription(Major.valueOf(major)), getMajorDescription(Major.valueOf(major)));
	}

	private String getMajorDescription(Major major) {
		switch (major) {
			case ASEDS :
				return "Architecture des Systèmes Embarqués et Distribués";
			case AMOA :
				return "Assistance à Maîtrise d'Ouvrage";
			case CLOUD :
				return "Cloud Computing and Infrastructure";
			case SESNUM :
				return "Sécurité des Systèmes Numériques";
			case SMART :
				return "Smart Systems and IoT";
			case DATA :
				return "Data Science and Analytics";
			case CYBER_SECURITY :
				return "Cybersecurity and Information Security";
			default :
				return "Information Technology";
		}
	}

	private Domain getDomainForMajor(Major major) {
		switch (major) {
			case ASEDS :
				return Domain.SOFTWARE_ENGINEERING;
			case DATA :
				return Domain.DATA_SCIENCE;
			case CYBER_SECURITY :
				return Domain.CYBERSECURITY;
			case CLOUD :
				return Domain.CLOUD_COMPUTING;
			case SMART :
				return Domain.INTERNET_OF_THINGS;
			case AMOA :
				return Domain.PRODUCT_MANAGEMENT;
			default :
				return Domain.SOFTWARE_ENGINEERING;
		}
	}

	private String generatePreviousPosition(String currentPosition) {
		if (currentPosition.contains("Senior")) {
			return currentPosition.replace("Senior ", "");
		} else if (currentPosition.contains("Lead")) {
			return currentPosition.replace("Lead", "Senior");
		} else {
			return "Junior " + currentPosition;
		}
	}

	private String generateExperienceDescription(String jobTitle) {
		return String.format("Worked as %s, contributing to various projects and gaining valuable experience "
				+ "in software development, system design, and team collaboration. "
				+ "Involved in the complete software development lifecycle from requirements analysis to deployment.",
				jobTitle);
	}
}

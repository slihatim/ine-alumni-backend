import { useParams } from "react-router";
import { LaureateDetailsHeader } from "@/components/laureats/LaureateDetailsHeader";
import { Timeline } from "@/components/common/Timeline";
import { SkillsList } from "@/components/laureats/SkillsList";
import { InfoSection } from "@/components/common/InfoSection";
import {
  sampleLaureates,
  sampleExperiences,
  sampleSkills,
  sampleEducation,
} from "@/data/sampleData";

export function LaureateDetailPage() {
  const { id } = useParams();
  const laureate = sampleLaureates.find((l) => l.id === id);

  if (!laureate) {
    return (
      <div className="min-h-screen bg-gray-50 py-8 text-center">
        <h1 className="text-2xl font-bold">Lauréat non trouvé</h1>
      </div>
    );
  }

  const aboutContent = (
    <div className="space-y-4">
      <p className="text-gray-700 leading-relaxed">
        Ingénieur logiciel avec plus de 3 ans d'expérience dans le développement
        d'applications web modernes. Spécialisé dans les technologies React et
        Node.js, avec une passion pour l'innovation et les solutions techniques
        créatives.
      </p>
      <p className="text-gray-700 leading-relaxed">
        Diplômé de l'INPT en 2021, j'ai évolué rapidement dans le domaine du
        développement logiciel et j'encadre maintenant des équipes junior.
      </p>
    </div>
  );

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 space-y-4">
        <LaureateDetailsHeader
          name={laureate.name}
          photoUrl={laureate.photoUrl}
          title={laureate.title}
          major={laureate.major}
          company={laureate.company}
          location={laureate.location}
        />

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-4">
          <div className="lg:col-span-2 space-y-4">
            <InfoSection
              title="À propos"
              content={aboutContent}
              className="bg-white p-6 rounded-lg shadow-sm border"
            />

            <div className="bg-white p-6 rounded-lg shadow-sm border">
              <h2 className="text-lg font-semibold text-gray-900 border-b border-gray-200 pb-2 mb-4">
                Formation
              </h2>
              <Timeline
                items={sampleEducation.map((edu) => ({
                  ...edu,
                  title: edu.degree,
                  subtitle: edu.institution,
                }))}
              />
            </div>

            <div className="bg-white p-6 rounded-lg shadow-sm border">
              <h2 className="text-lg font-semibold text-gray-900 border-b border-gray-200 pb-2 mb-4">
                Expériences
              </h2>
              <Timeline
                items={sampleExperiences.map((exp) => ({
                  ...exp,
                  subtitle: exp.company,
                }))}
              />
            </div>
          </div>

          <div className="space-y-6">
            <div className="bg-white p-6 rounded-lg shadow-sm border">
              <h3 className="text-lg font-semibold text-gray-900 mb-4">
                Compétences
              </h3>
              <SkillsList skills={sampleSkills} />
            </div>

            <div className="bg-white p-6 rounded-lg shadow-sm border">
              <h3 className="text-lg font-semibold text-gray-900 mb-4">
                Liens externes
              </h3>
              <div className="space-y-2">
                <a
                  href={laureate.linkedinUrl}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="flex items-center gap-2 text-main-blue hover:underline"
                >
                  LinkedIn
                </a>
                <a
                  href={`mailto:${laureate.email}`}
                  className="flex items-center gap-2 text-main-blue hover:underline"
                >
                  Email
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

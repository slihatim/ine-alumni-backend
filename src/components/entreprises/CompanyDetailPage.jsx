import { useParams } from "react-router";
import { useState, useEffect } from "react";
import { CompanyDetailsHeader } from "@/components/entreprises/CompanyDetailsHeader";
import { HRContactSection } from "@/components/entreprises/HRContactSection";
import { AlumniList } from "@/components/entreprises/AlumniList";
import { ReviewsList } from "@/components/entreprises/ReviewsList";
import { InfoSection } from "@/components/common/InfoSection";
import { companiesService } from "../../services/companiesService";

export function CompanyDetailPage() {
  const { id } = useParams();
  const [company, setCompany] = useState(null);
  const [alumni, setAlumni] = useState([]);
  const [reviews, setReviews] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (id) {
      fetchCompanyData();
    }
  }, [id]);

  const fetchCompanyData = async () => {
    try {
      setLoading(true);
      setError(null);

      // Fetch company details, alumni, and reviews in parallel
      const [companyResponse, alumniResponse, reviewsResponse] =
        await Promise.all([
          companiesService.getCompanyById(id),
          companiesService.getCompanyAlumni(id),
          companiesService.getCompanyReviews(id),
        ]);

      setCompany(companyResponse);
      setAlumni(alumniResponse.content || []);
      setReviews(reviewsResponse.content || []);
    } catch (err) {
      console.error("Error fetching company data:", err);
      setError("Failed to load company information. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  const handleAlumniClick = (alumni) => {
    console.log("Clicked alumni:", alumni);
    // Navigate to alumni detail page
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-50 py-8">
        <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-center items-center py-12">
            <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
          </div>
        </div>
      </div>
    );
  }

  if (error || !company) {
    return (
      <div className="min-h-screen bg-gray-50 py-8">
        <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded">
            {error || "Company not found"}
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 space-y-4">
        <CompanyDetailsHeader
          name={company.name}
          logoUrl={company.logo}
          location={company.location || "Maroc"}
          website={company.website || ""}
          domain={company.industry}
        />

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-4">
          <div className="lg:col-span-2 space-y-4">
            <InfoSection
              title="Présentation"
              content={
                <div className="space-y-4">
                  <p className="text-gray-700 leading-relaxed">
                    {company.description}
                  </p>
                </div>
              }
              className="bg-white p-6 rounded-lg shadow-sm border"
            />

            <AlumniList
              alumni={alumni.map((alumnus) => ({
                id: alumnus.id,
                name: alumnus.fullName,
                photoUrl: alumnus.profilePicture,
                promotion: alumnus.graduationYear?.toString(),
                title: alumnus.currentPosition,
                major: alumnus.major,
                company: company.name,
                location: `${alumnus.city}, ${alumnus.country}`,
                linkedinUrl: alumnus.externalLinks?.find(
                  (link) => link.linkType === "LINKEDIN"
                )?.url,
                email: alumnus.email,
              }))}
              onAlumniClick={handleAlumniClick}
              className="bg-white p-6 rounded-lg shadow-sm border"
            />

            <ReviewsList
              reviews={reviews.map((review) => ({
                id: review.id,
                author: review.alumniName,
                timeAgo: new Date(review.createdAt).toLocaleDateString("fr-FR"),
                rating: review.rating,
                comment: review.comment,
                authorAvatar: review.alumniProfilePicture,
              }))}
              className="bg-white p-6 rounded-lg shadow-sm border"
            />
          </div>

          <div className="space-y-6">
            <div className="bg-white p-6 rounded-lg shadow-sm border">
              <h3 className="text-lg font-semibold text-gray-900 mb-4">
                Contacts RH
              </h3>
              <HRContactSection
                contacts={[
                  ...(company.hrContactEmail
                    ? [
                        {
                          title: "Email",
                          description: "Contactez-nous directement",
                          email: company.hrContactEmail,
                        },
                      ]
                    : []),
                  ...(company.externalLinks
                    ?.filter((link) => link.linkType === "LINKEDIN")
                    .map((link) => ({
                      title: "LinkedIn",
                      description: "Suivez-nous sur LinkedIn",
                      linkedin: link.url,
                    })) || []),
                  ...(company.website
                    ? [
                        {
                          title: "Site Web",
                          description: "Visitez notre site",
                          website: company.website,
                        },
                      ]
                    : []),
                ]}
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

import React, { useState, useEffect, useCallback } from "react";
import { useNavigate } from "react-router";
import { CompanyCard } from "./CompanyCard";
import { SearchBarWithFilters } from "../layout/SearchBarWithFilters";
import { FilterPanel } from "../common/FilterPanel";
import { companiesService } from "../../services/companiesService";
import { companyFilters } from "../../data/sampleData";
const Entreprises = () => {
  const [searchQuery, setSearchQuery] = useState("");
  const [filters, setFilters] = useState({});
  const [companies, setCompanies] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [pagination, setPagination] = useState({
    pageNumber: 0,
    pageSize: 12,
    totalElements: 0,
    totalPages: 0,
  });
  const navigate = useNavigate();

  const fetchCompanies = useCallback(async () => {
    try {
      setLoading(true);
      setError(null);

      let response;
      if (searchQuery.trim()) {
        response = await companiesService.searchCompanies(searchQuery, {
          page: pagination.pageNumber,
          size: pagination.pageSize,
        });
      } else {
        response = await companiesService.getAllCompanies({
          page: pagination.pageNumber,
          size: pagination.pageSize,
        });
      }

      setCompanies(response.content || []);
      setPagination({
        pageNumber: response.pageNumber || 0,
        pageSize: response.pageSize || 12,
        totalElements: response.totalElements || 0,
        totalPages: response.totalPages || 0,
      });
    } catch (err) {
      console.error("Error fetching companies:", err);
      setError("Failed to load companies. Please try again.");
      setCompanies([]);
    } finally {
      setLoading(false);
    }
  }, [searchQuery, filters, pagination.pageNumber, pagination.pageSize]);

  // Fetch companies from backend
  useEffect(() => {
    fetchCompanies();
  }, [fetchCompanies]);

  const handleCompanyClick = (company) => {
    console.log("Clicked company:", company);
    // Navigate to company detail page
    navigate(`/entreprises/${company.id}`);
  };

  return (
    <div className="min-h-screen  py-8">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="mb-8">
          <h1 className="text-3xl font-bold text-gray-900 mb-2">
            Annuaires des Entreprises
          </h1>
          <p className="text-gray-600">
            Trouvez les entreprises associées à notre réseau et connectez-vous à
            leurs contacts
          </p>
        </div>

        <div className="mb-8">
          <SearchBarWithFilters
            placeholder="Recherche sur nom, domaine, localisation..."
            onSearch={setSearchQuery}
            filters={
              <FilterPanel filters={companyFilters} onChange={setFilters} />
            }
          />
        </div>

        {error && (
          <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded mb-6">
            {error}
          </div>
        )}

        {loading ? (
          <div className="flex justify-center items-center py-12">
            <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
          </div>
        ) : (
          <>
            <div className="mb-6 flex items-center justify-between">
              <p className="text-sm text-gray-600">
                {pagination.totalElements} entreprises trouvées
              </p>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-6">
              {companies.map((company) => (
                <CompanyCard
                  key={company.id}
                  name={company.name}
                  logoUrl={company.logo}
                  domain={company.industry}
                  laureatesCount={company.alumniCount}
                  onClick={() => handleCompanyClick(company)}
                />
              ))}
            </div>

            {companies.length === 0 && !loading && (
              <div className="text-center py-12">
                <p className="text-gray-500">Aucune entreprise trouvée.</p>
              </div>
            )}
          </>
        )}
      </div>
    </div>
  );
};

export default Entreprises;

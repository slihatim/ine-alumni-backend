import { useState, useMemo, useEffect } from 'react';
import { Button } from '../ui/button';
import { Input } from '../ui/input';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '../ui/select';
import { Card, CardContent, CardHeader, CardTitle } from '../ui/card';
import { Badge } from '../ui/badge';
import { Search, MapPin, Building, Calendar, Filter, Clock, ChevronDown, ChevronRight } from 'lucide-react';
import { useLanguage } from '../contexts/LanguageContext.jsx';

const API_BASE = import.meta.env.VITE_API_BASE || '/api/v1';

// Mock data for demonstration - will be replaced by API data when available
const mockJobs = [
  {
    id: '1',
    title: 'Stage Développeur Frontend React',
    company: 'TechCorp Innovation',
    location: 'Paris, France',
    type: 'stage',
    customType: '',
    duration: '6 mois',
    description: `Rejoignez notre équipe de développement frontend pour travailler sur des projets innovants utilisant React et TypeScript.

**Responsabilités:**
• Développer des interfaces utilisateur modernes et responsive
• Collaborer avec l'équipe UX/UI pour implémenter des designs
• Participer aux revues de code et aux réunions d'équipe
• Maintenir et améliorer les applications existantes

**Compétences requises:**
• Maîtrise de JavaScript/TypeScript
• Expérience avec React et ses écosystèmes
• Connaissance de HTML5, CSS3 et Tailwind CSS
• Familiarité avec Git et les workflows collaboratifs

**Ce que nous offrons:**
• Environnement de travail stimulant et collaboratif
• Mentorat par des développeurs expérimentés
• Possibilité de télétravail hybride
• Gratification attractive selon convention`,
    posted: '2025-01-15',
    isNew: true,
    isRemote: false,
    externalLink: 'https://techcorp.com/careers/frontend-intern',
  }
];

// API mapping helper - converts API response to UI format
const mapApiOfferToUi = (o) => {
  const type = (o.type || '').toLowerCase();
  const mappedType = type === 'internship' ? 'stage'
    : type === 'job' ? 'emploi'
    : type === 'alternance' ? 'alternance'
    : 'autre';
  const customType = type === 'benevolat' ? 'Bénévolat' : (o.customType || '');
  const posted = o.postedAt || new Date().toISOString();
  const isNew = (() => {
    try {
      const d = new Date(posted);
      return (Date.now() - d.getTime()) <= 7 * 24 * 60 * 60 * 1000;
    } catch { return false; }
  })();
  return {
    id: String(o.id),
    title: o.title,
    company: o.company,
    location: o.location,
    type: mappedType,
    customType,
    duration: o.duration || '',
    description: o.description || '',
    posted,
    isNew,
    isRemote: false,
    externalLink: o.link || null,
    __raw: o,
  };
};

/**
 * JobListing Component
 * 
 * Main component for displaying job listings with search, filter, and expandable card functionality.
 * Features:
 * - Real-time search across job titles, companies, and custom types
 * - Filter by job type and location
 * - Expandable job cards with arrow toggle (► to ▼)
 * - Responsive design with proper spacing
 * - White cards with gray headers and proper borders
 * 
 * @param {Object} props
 * @param {Function} props.onJobSelect - Callback when a job is selected for detailed view
 */
export function JobListing({ onJobSelect }) {
  const { t } = useLanguage();
  
  // Search and filter state
  const [searchTerm, setSearchTerm] = useState('');
  const [typeFilter, setTypeFilter] = useState('all');
  const [locationFilter, setLocationFilter] = useState('all');
  const [showFilters, setShowFilters] = useState(false);
  
  // Data state
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  
  // Expandable cards state - tracks which cards are expanded
  const [expandedCards, setExpandedCards] = useState(new Set());

  /**
   * Fetch jobs from API on component mount
   * Falls back to mock data if API is not available
   */
  useEffect(() => {
    let isMounted = true;
    (async () => {
      setLoading(true);
      setError('');
      try {
        const res = await fetch(`${API_BASE}/offers`);
        if (!res.ok) {
          // If API fails, use mock data for demonstration
          if (isMounted) {
            setJobs(mockJobs);
            console.warn('API not available, using mock data');
          }
          return;
        }
        const data = await res.json();
        if (isMounted) setJobs((data || []).map(mapApiOfferToUi));
      } catch (e) {
        // Fallback to mock data on error
        if (isMounted) {
          setJobs(mockJobs);
          console.warn('API error, using mock data:', e.message);
        }
      } finally {
        if (isMounted) setLoading(false);
      }
    })();
    return () => { isMounted = false; };
  }, []);

  // Extract unique values for filter dropdowns
  const companies = [...new Set(jobs.map(job => job.company))];
  const locations = [...new Set(jobs.map(job => job.location))];

  /**
   * Filter jobs based on search term and selected filters
   * Searches across job title, company name, and custom type
   */
  const filteredJobs = useMemo(() => {
    return jobs.filter(job => {
      const matchesSearch = job.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
                           job.company.toLowerCase().includes(searchTerm.toLowerCase()) ||
                           (job.customType && job.customType.toLowerCase().includes(searchTerm.toLowerCase()));
      const matchesType = typeFilter === 'all' || job.type === typeFilter;
      const matchesLocation = locationFilter === 'all' || job.location === locationFilter;
      
      return matchesSearch && matchesType && matchesLocation;
    });
  }, [jobs, searchTerm, typeFilter, locationFilter]);

  /**
   * Format date for display in French locale
   */
  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleDateString('fr-FR', { 
      year: 'numeric', 
      month: 'long', 
      day: 'numeric' 
    });
  };

  /**
   * Get appropriate color scheme for job type badge
   */
  const getTypeColor = (type) => {
    switch (type) {
      case 'stage':
        return 'bg-blue-50 text-blue-700 border-blue-200';
      case 'emploi':
        return 'bg-green-50 text-green-700 border-green-200';
      case 'alternance':
        return 'bg-purple-50 text-purple-700 border-purple-200';
      case 'autre':
        return 'bg-orange-50 text-orange-700 border-orange-200';
      default:
        return 'bg-gray-50 text-gray-700 border-gray-200';
    }
  };

  /**
   * Get display text for job type, handling custom types
   */
  const getTypeDisplayText = (job) => {
    if (job.type === 'autre' && job.customType) {
      return job.customType;
    }
    return t(`type.${job.type}`);
  };

  /**
   * Toggle expanded state of a job card
   */
  const toggleCardExpansion = (jobId) => {
    setExpandedCards(prev => {
      const newSet = new Set(prev);
      if (newSet.has(jobId)) {
        newSet.delete(jobId);
      } else {
        newSet.add(jobId);
      }
      return newSet;
    });
  };

  /**
   * Format job description for display
   * Handles markdown-like formatting (**, •)
   */
  const formatDescription = (description) => {
    return description.split('\n').map((paragraph, index) => {
      if (paragraph.startsWith('**') && paragraph.endsWith('**')) {
        return (
          <h4 key={index} className="font-semibold text-gray-900 mt-4 mb-2 first:mt-0">
            {paragraph.slice(2, -2)}
          </h4>
        );
      }
      if (paragraph.startsWith('•')) {
        return (
          <div key={index} className="flex items-start space-x-2 mb-1">
            <span className="text-blue-600 font-bold">•</span>
            <span className="text-gray-700 text-sm">{paragraph.slice(2)}</span>
          </div>
        );
      }
      if (paragraph.trim()) {
        return (
          <p key={index} className="mb-3 text-gray-700 text-sm">
            {paragraph}
          </p>
        );
      }
      return <br key={index} />;
    });
  };

  return (
    <div className="space-y-6 pb-8" style={{ fontFamily: 'Open Sans, sans-serif' }}>
      {/* Header */}
      <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
        <h1 className="text-[22px] font-bold text-[#053A5F]" style={{ fontFamily: 'Open Sans, sans-serif' }}>
          {t('jobs.title')}
        </h1>
        <Button
          variant="outline"
          onClick={() => setShowFilters(!showFilters)}
          className="flex items-center space-x-2 md:hidden border-[#3A7FC2] text-[#053A5F] hover:bg-[#E2F2FF]"
        >
          <Filter className="w-4 h-4" />
          <span>Filtrer</span>
        </Button>
      </div>

      {/* Search Bar */}
      <div className="relative">
        <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-[#3A7FC2] w-4 h-4" />
        <Input
          placeholder="Rechercher par titre, entreprise ou type..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="pl-10 border-[#3A7FC2] focus:border-[#0C5F95] focus:ring-[#E2F2FF] bg-white"
          style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}
        />
      </div>

      {/* Filters */}
      <div className={`grid grid-cols-1 md:grid-cols-3 gap-4 ${showFilters ? 'block' : 'hidden md:grid'}`}>
        <Select value={typeFilter} onValueChange={setTypeFilter}>
          <SelectTrigger className="border-[#3A7FC2] focus:border-[#0C5F95] focus:ring-[#E2F2FF] bg-white">
            <SelectValue placeholder="Type d'offre" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="all">Tous les types</SelectItem>
            <SelectItem value="stage">{t('type.stage')}</SelectItem>
            <SelectItem value="alternance">{t('type.alternance')}</SelectItem>
            <SelectItem value="emploi">{t('type.emploi')}</SelectItem>
            <SelectItem value="autre">{t('type.autre')}</SelectItem>
          </SelectContent>
        </Select>

        <Select value={locationFilter} onValueChange={setLocationFilter}>
          <SelectTrigger className="border-[#3A7FC2] focus:border-[#0C5F95] focus:ring-[#E2F2FF] bg-white">
            <SelectValue placeholder="Localisation" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="all">Tous les lieux</SelectItem>
            {locations.map(location => (
              <SelectItem key={location} value={location}>{location}</SelectItem>
            ))}
          </SelectContent>
        </Select>

        <Button
          variant="outline"
          onClick={() => {
            setSearchTerm('');
            setTypeFilter('all');
            setLocationFilter('all');
          }}
          className="border-[#3A7FC2] text-[#053A5F] hover:bg-[#E2F2FF] bg-white"
        >
          {t('common.reset')}
        </Button>
      </div>

      {/* Results Count */}
      <div className="text-sm text-[#3A7FC2]" style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}>
        {loading ? 'Chargement...' : error ? 'Erreur de chargement' : `${filteredJobs.length} résultat(s)`}
      </div>

      {/* Job Cards */}
      <div className="space-y-6">
        {filteredJobs.length === 0 ? (
          <Card className="p-8 text-center bg-white border-[#3A7FC2] shadow-sm">
            <p className="text-[#053A5F]" style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}>
              Aucune offre ne correspond à vos critères de recherche.
            </p>
          </Card>
        ) : (
          filteredJobs.map(job => {
            const isExpanded = expandedCards.has(job.id);
            
            return (
              <Card 
                key={job.id} 
                className="bg-white border-2 border-[#0C5F95] shadow-md hover:shadow-lg transition-all duration-200 overflow-hidden"
              >
                {/* Card Header - Gray background with job info */}
                <CardHeader className="pb-3 bg-gray-50 border-b border-gray-200">
                  <div className="flex flex-col sm:flex-row justify-between items-start gap-3">
                    <div className="space-y-2 flex-1">
                      <div className="flex items-center gap-2 flex-wrap">
                        {/* Expandable arrow and title */}
                        <div className="flex items-center gap-2">
                          <button
                            onClick={() => toggleCardExpansion(job.id)}
                            className="text-[#0C5F95] hover:text-[#053A5F] transition-colors p-1 rounded"
                            aria-label={isExpanded ? 'Réduire' : 'Développer'}
                          >
                            {isExpanded ? (
                              <ChevronDown className="w-5 h-5" />
                            ) : (
                              <ChevronRight className="w-5 h-5" />
                            )}
                          </button>
                          <CardTitle 
                            className="text-[18px] font-semibold text-[#053A5F] cursor-pointer" 
                            style={{ fontFamily: 'Open Sans, sans-serif' }}
                            onClick={() => toggleCardExpansion(job.id)}
                          >
                            {job.title}
                          </CardTitle>
                        </div>
                        
                        {/* Badges */}
                        {job.isNew && (
                          <Badge variant="secondary" className="bg-[#0C5F95] text-white border-[#0C5F95]">
                            {t('common.new')}
                          </Badge>
                        )}
                        {job.isRemote && (
                          <Badge variant="outline" className="border-[#3A7FC2] text-[#053A5F] bg-white">
                            {t('common.remote')}
                          </Badge>
                        )}
                      </div>
                      
                      {/* Job metadata */}
                      <div className="flex items-center space-x-4 text-sm text-[#3A7FC2] flex-wrap gap-y-1" style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}>
                        <div className="flex items-center space-x-1">
                          <Building className="w-4 h-4" />
                          <span>{job.company}</span>
                        </div>
                        <div className="flex items-center space-x-1">
                          <MapPin className="w-4 h-4" />
                          <span>{job.location}</span>
                        </div>
                        <div className="flex items-center space-x-1">
                          <Clock className="w-4 h-4" />
                          <span>{job.duration}</span>
                        </div>
                        <div className="flex items-center space-x-1">
                          <Calendar className="w-4 h-4" />
                          <span>{formatDate(job.posted)}</span>
                        </div>
                      </div>
                    </div>
                    
                    {/* Job type badge */}
                    <div className="flex flex-col gap-2">
                      <Badge 
                        className={`${getTypeColor(job.type)} font-semibold border`}
                        style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}
                      >
                        {getTypeDisplayText(job)}
                      </Badge>
                    </div>
                  </div>
                </CardHeader>

                {/* Card Content - White background with description preview or full description */}
                <CardContent className="bg-white">
                  {!isExpanded ? (
                    /* Collapsed state - show preview */
                    <p className="text-[#053A5F] line-clamp-2 pt-3" style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}>
                      {job.description.split('\n')[0]}
                    </p>
                  ) : (
                    /* Expanded state - show full description */
                    <div className="pt-3 space-y-2">
                      <div className="prose prose-sm max-w-none">
                        {formatDescription(job.description)}
                      </div>
                      
                      {/* Action buttons when expanded */}
                      <div className="flex flex-col sm:flex-row gap-3 pt-4 border-t border-gray-100">
                        <Button
                          onClick={() => onJobSelect(job)}
                          className="bg-[#0C5F95] hover:bg-[#053A5F] text-white shadow-md flex-1 sm:flex-none"
                          style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}
                        >
                          {t('common.apply')}
                        </Button>
                        
                        {job.externalLink && (
                          <Button
                            variant="outline"
                            onClick={() => window.open(job.externalLink, '_blank')}
                            className="border-[#3A7FC2] text-[#053A5F] hover:bg-[#E2F2FF] flex-1 sm:flex-none"
                            style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}
                          >
                            Voir l'offre originale
                          </Button>
                        )}
                      </div>
                    </div>
                  )}
                </CardContent>
              </Card>
            );
          })
        )}
      </div>
    </div>
  );
}
import React, { useState, useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import { JobListing } from './JobListing';
import { JobSubmission } from './JobSubmission';
import { JobDetails } from './JobDetails.jsx';
import { Button } from '../ui/button';
import { useLanguage, LanguageProvider } from '../contexts/LanguageContext.jsx';

// Main Jobs component content that uses the language context
const JobsContent = () => {
  const [currentView, setCurrentView] = useState('jobs');
  const [selectedJob, setSelectedJob] = useState(null);
  const [searchParams, setSearchParams] = useSearchParams();
  const { t, language } = useLanguage();

  // Handle URL parameters
  useEffect(() => {
    const viewParam = searchParams.get('view');
    if (viewParam === 'submit') {
      setCurrentView('submit');
    } else {
      setCurrentView('jobs');
    }
  }, [searchParams]);

  const handleViewChange = (view) => {
    setCurrentView(view);
    if (view === 'submit') {
      setSearchParams({ view: 'submit' });
    } else {
      setSearchParams({});
    }
  };

  const handleJobSelect = (job) => {
    setSelectedJob(job);
  };

  const handleBackToJobs = () => {
    setSelectedJob(null);
  };

  // Navigation text
  const navText = {
    offers: { fr: 'Offres', en: 'Offers' },
    submit: { fr: 'Soumettre', en: 'Submit' }
  };

  const getNavText = (key) => navText[key]?.[language] || navText[key]?.fr;

  // If a job is selected, show job details
  if (selectedJob) {
    return <JobDetails job={selectedJob} onBack={handleBackToJobs} />;
  }

  // Otherwise show jobs listing or submission form with navigation
  return (
    <div className="max-w-6xl mx-auto px-4" style={{ fontFamily: 'Open Sans, sans-serif' }}>
      <div className="space-y-6">
        {/* Internal Navigation - Below sidebar area */}
        <div className="flex justify-center space-x-4 mt-8">
          <Button
            variant={currentView === 'jobs' ? 'default' : 'outline'}
            onClick={() => handleViewChange('jobs')}
            className={`px-6 py-3 text-sm font-semibold transition-all duration-200 ${
              currentView === 'jobs' 
                ? 'bg-[#0C5F95] hover:bg-[#053A5F] text-white shadow-md' 
                : 'bg-white border-[#3A7FC2] text-[#053A5F] hover:bg-[#E2F2FF]'
            }`}
            style={{ fontFamily: 'Open Sans, sans-serif' }}
          >
            {getNavText('offers')}
          </Button>
          <Button
            variant={currentView === 'submit' ? 'default' : 'outline'}
            onClick={() => handleViewChange('submit')}
            className={`px-6 py-3 text-sm font-semibold transition-all duration-200 ${
              currentView === 'submit' 
                ? 'bg-[#0C5F95] hover:bg-[#053A5F] text-white shadow-md' 
                : 'bg-white border-[#3A7FC2] text-[#053A5F] hover:bg-[#E2F2FF]'
            }`}
            style={{ fontFamily: 'Open Sans, sans-serif' }}
          >
            {getNavText('submit')}
          </Button>
        </div>

        {/* Content */}
        <div>
          {currentView === 'jobs' ? (
            <JobListing onJobSelect={handleJobSelect} />
          ) : (
            <JobSubmission />
          )}
        </div>
      </div>
    </div>
  );
};

// Wrapper component that provides language context to all job-related components
const Jobs = () => {
  return (
    <LanguageProvider>
      <JobsContent />
    </LanguageProvider>
  );
};

export default Jobs;
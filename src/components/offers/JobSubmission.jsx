import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Button } from '../ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '../ui/card';
import { Input } from '../ui/input';
import { Textarea } from '../ui/textarea.jsx';
import { Label } from '../ui/label.jsx';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '../ui/select';
import { useLanguage } from '../contexts/LanguageContext.jsx';
import { toast } from 'sonner';

const API_BASE = import.meta.env.VITE_API_BASE || '/api/v1';

/**
 * JobSubmission Component
 * 
 * Form component for submitting new job offers to the platform.
 * Features:
 * - Clean white cards with gray headers and proper borders
 * - Dynamic form fields that adapt based on job type selection
 * - Form validation with user-friendly error messages
 * - Guidelines card with helpful tips for quality submissions
 * - Responsive design with proper spacing and typography
 * - Toast notifications for submission feedback
 * 
 * Form includes:
 * - Basic job information (title, company, location)
 * - Job type selection with custom type option
 * - Duration field with context-aware placeholders
 * - Rich description field with formatting guidelines
 * - Optional external link for original job posting
 */
export function JobSubmission() {
  const { t } = useLanguage();
  const navigate = useNavigate();
  
  // Form state management
  const [formData, setFormData] = useState({
    title: '',
    company: '',
    location: '',
    type: '',
    customType: '',
    duration: '',
    description: '',
    link: '',
  });
  const [isSubmitting, setIsSubmitting] = useState(false);

  /**
   * Handle form submission with validation
   */
  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);

    // Client-side validation
    if (!formData.title || !formData.company || !formData.location || !formData.type || !formData.description) {
      toast.error(t('validation.required'));
      setIsSubmitting(false);
      return;
    }

    // Validate custom type if "autre" is selected
    if (formData.type === 'autre' && !formData.customType.trim()) {
      toast.error(t('validation.custom_type'));
      setIsSubmitting(false);
      return;
    }

    try {
      /**
       * Normalize job type for API compatibility
       */
      const normalizeType = (type) => {
        switch (type) {
          case 'stage':
            return 'internship';
          case 'emploi':
            return 'job';
          case 'alternance':
            return 'alternance';
          case 'autre':
            return 'other';
          default:
            return 'other';
        }
      };

      // Prepare payload for API
      const payload = {
        title: formData.title.trim(),
        company: formData.company.trim(),
        location: formData.location.trim(),
        type: normalizeType(formData.type),
        customType: formData.type === 'autre' ? formData.customType.trim() : null,
        duration: formData.duration?.trim() || null,
        description: formData.description,
        link: formData.link?.trim() || null,
      };

      // Submit to API
      const res = await fetch(`${API_BASE}/offers`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
      });

      if (!res.ok) {
        const text = await res.text();
        throw new Error(text || 'Failed to submit');
      }

      toast.success(t('submit.success'));

      // Reset form after successful submission
      setFormData({
        title: '',
        company: '',
        location: '',
        type: '',
        customType: '',
        duration: '',
        description: '',
        link: '',
      });

      // Navigate back to job listings
      navigate('/emplois');
    } catch (err) {
      toast.error(err.message || 'Submission failed');
    } finally {
      setIsSubmitting(false);
    }
  };

  /**
   * Reset form to initial state
   */
  const handleReset = () => {
    setFormData({
      title: '',
      company: '',
      location: '',
      type: '',
      customType: '',
      duration: '',
      description: '',
      link: '',
    });
  };

  /**
   * Update form data with automatic cleanup
   */
  const updateFormData = (field, value) => {
    setFormData(prev => {
      const newData = { ...prev, [field]: value };
      // Reset custom type when changing from "autre" to something else
      if (field === 'type' && value !== 'autre') {
        newData.customType = '';
      }
      return newData;
    });
  };

  /**
   * Get context-aware placeholder for duration field
   */
  const getDurationPlaceholder = () => {
    switch (formData.type) {
      case 'stage':
        return 'ex: 3-6 mois';
      case 'alternance':
        return 'ex: 12-24 mois';
      case 'emploi':
        return 'ex: CDI, CDD 12 mois';
      case 'autre':
        return formData.customType ? `ex: Durée pour ${formData.customType}` : 'ex: Variable, à définir';
      default:
        return 'ex: 6 mois, CDI, CDD';
    }
  };

  /**
   * Get context-aware placeholder for description field
   */
  const getDescriptionPlaceholder = () => {
    const commonText = `Décrivez l'offre en détail:

• Responsabilités principales
• Compétences requises
• Profil recherché`;

    switch (formData.type) {
      case 'stage':
        return `${commonText}
• Missions du stage
• Encadrement et mentorat
• Gratification et avantages`;
      case 'alternance':
        return `${commonText}
• Programme de formation
• Rythme alternance/formation
• Diplôme préparé
• Perspectives d'embauche`;
      case 'emploi':
        return `${commonText}
• Évolutions de carrière
• Rémunération et avantages
• Conditions de travail`;
      case 'autre':
        return `${commonText}
• Spécificités de ce type d'offre
• Modalités particulières
• Avantages et conditions`;
      default:
        return `${commonText}
• Avantages offerts
• Conditions (durée, rémunération, etc.)`;
    }
  };

  return (
    <div className="space-y-6 max-w-4xl mx-auto pb-8" style={{ fontFamily: 'Open Sans, sans-serif' }}>
      {/* Header */}
      <div className="text-center space-y-2">
        <h1 className="text-[22px] font-bold text-[#053A5F]" style={{ fontFamily: 'Open Sans, sans-serif' }}>
          {t('submit.title')}
        </h1>
        <p className="text-[#3A7FC2]" style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}>
          Partagez une nouvelle opportunité avec la communauté alumni
        </p>
      </div>

      {/* Main Submission Form Card */}
      <Card className="bg-white border-2 border-[#0C5F95] shadow-md">
        <CardHeader className="bg-gray-50 border-b border-gray-200">
          <CardTitle className="text-[20px] font-semibold text-[#053A5F]" style={{ fontFamily: 'Open Sans, sans-serif' }}>
            Informations de l'offre
          </CardTitle>
        </CardHeader>
        <CardContent className="p-6 bg-white">
          <form onSubmit={handleSubmit} className="space-y-6">
            {/* Job Title - Full width */}
            <div className="space-y-2">
              <Label htmlFor="title" className="text-[#053A5F] font-semibold" style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}>
                {t('submit.job_title')} <span className="text-red-500">*</span>
              </Label>
              <Input
                id="title"
                value={formData.title}
                onChange={(e) => updateFormData('title', e.target.value)}
                placeholder="ex: Développeur Full Stack Junior, Stage Marketing Digital, Alternance Data Analyst"
                className="border-[#3A7FC2] focus:border-[#0C5F95] focus:ring-[#E2F2FF] bg-white"
                style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}
                required
              />
            </div>

            {/* Company and Location Row */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div className="space-y-2">
                <Label htmlFor="company" className="text-[#053A5F] font-semibold" style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}>
                  {t('submit.company_name')} <span className="text-red-500">*</span>
                </Label>
                <Input
                  id="company"
                  value={formData.company}
                  onChange={(e) => updateFormData('company', e.target.value)}
                  placeholder="ex: TechCorp, StartupInnovante"
                  className="border-[#3A7FC2] focus:border-[#0C5F95] focus:ring-[#E2F2FF] bg-white"
                  style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}
                  required
                />
              </div>

              <div className="space-y-2">
                <Label htmlFor="location" className="text-[#053A5F] font-semibold" style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}>
                  {t('submit.location')} <span className="text-red-500">*</span>
                </Label>
                <Input
                  id="location"
                  value={formData.location}
                  onChange={(e) => updateFormData('location', e.target.value)}
                  placeholder="ex: Paris, France ou Remote"
                  className="border-[#3A7FC2] focus:border-[#0C5F95] focus:ring-[#E2F2FF] bg-white"
                  style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}
                  required
                />
              </div>
            </div>

            {/* Job Type and Custom Type Row */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div className="space-y-2">
                <Label htmlFor="type" className="text-[#053A5F] font-semibold" style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}>
                  {t('submit.type')} <span className="text-red-500">*</span>
                </Label>
                <Select value={formData.type} onValueChange={(value) => updateFormData('type', value)}>
                  <SelectTrigger className="border-[#3A7FC2] focus:border-[#0C5F95] focus:ring-[#E2F2FF] bg-white">
                    <SelectValue placeholder="Sélectionnez le type d'offre" />
                  </SelectTrigger>
                  <SelectContent className="bg-white border-[#3A7FC2]">
                    <SelectItem value="stage">{t('type.stage')}</SelectItem>
                    <SelectItem value="alternance">{t('type.alternance')}</SelectItem>
                    <SelectItem value="emploi">{t('type.emploi')}</SelectItem>
                    <SelectItem value="autre">{t('type.autre')}</SelectItem>
                  </SelectContent>
                </Select>
              </div>

              {/* Custom Type Input - appears when "autre" is selected */}
              {formData.type === 'autre' && (
                <div className="space-y-2">
                  <Label htmlFor="customType" className="text-[#053A5F] font-semibold" style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}>
                    {t('submit.custom_type')} <span className="text-red-500">*</span>
                  </Label>
                  <Input
                    id="customType"
                    value={formData.customType}
                    onChange={(e) => updateFormData('customType', e.target.value)}
                    placeholder={t('submit.custom_type.placeholder')}
                    className="border-[#3A7FC2] focus:border-[#0C5F95] focus:ring-[#E2F2FF] bg-white"
                    style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}
                    required={formData.type === 'autre'}
                  />
                </div>
              )}

              {/* Duration Field */}
              <div className={`space-y-2 ${formData.type === 'autre' ? 'md:col-start-1' : ''}`}>
                <Label htmlFor="duration" className="text-[#053A5F] font-semibold" style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}>
                  {t('submit.duration')}
                </Label>
                <Input
                  id="duration"
                  value={formData.duration}
                  onChange={(e) => updateFormData('duration', e.target.value)}
                  placeholder={getDurationPlaceholder()}
                  className="border-[#3A7FC2] focus:border-[#0C5F95] focus:ring-[#E2F2FF] bg-white"
                  style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}
                />
              </div>
            </div>

            {/* Description - Full width */}
            <div className="space-y-2">
              <Label htmlFor="description" className="text-[#053A5F] font-semibold" style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}>
                {t('submit.description')} <span className="text-red-500">*</span>
              </Label>
              <Textarea
                id="description"
                value={formData.description}
                onChange={(e) => updateFormData('description', e.target.value)}
                rows={12}
                placeholder={getDescriptionPlaceholder()}
                className="border-[#3A7FC2] focus:border-[#0C5F95] focus:ring-[#E2F2FF] bg-white"
                style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}
                required
              />
              <p className="text-xs text-[#3A7FC2]" style={{ fontFamily: 'Open Sans, sans-serif' }}>
                Utilisez des listes à puces (•) et des titres (**titre**) pour structurer votre description
              </p>
            </div>

            {/* External Link - Full width */}
            <div className="space-y-2">
              <Label htmlFor="link" className="text-[#053A5F] font-semibold" style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}>
                {t('submit.link')}
              </Label>
              <Input
                id="link"
                type="url"
                value={formData.link}
                onChange={(e) => updateFormData('link', e.target.value)}
                placeholder="https://company.com/careers/job-posting"
                className="border-[#3A7FC2] focus:border-[#0C5F95] focus:ring-[#E2F2FF] bg-white"
                style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}
              />
              <p className="text-xs text-[#3A7FC2]" style={{ fontFamily: 'Open Sans, sans-serif' }}>
                Lien vers l'offre originale ou la page de candidature (optionnel)
              </p>
            </div>

            {/* Form Actions */}
            <div className="flex flex-col sm:flex-row gap-4 pt-6 border-t border-gray-200">
              <Button
                type="button"
                variant="outline"
                onClick={handleReset}
                className="flex-1 sm:flex-none border-[#3A7FC2] text-[#053A5F] hover:bg-[#E2F2FF] bg-white"
                style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}
              >
                {t('common.reset')}
              </Button>
              <Button
                type="submit"
                disabled={isSubmitting}
                className="flex-1 sm:flex-none bg-[#0C5F95] hover:bg-[#053A5F] text-white shadow-md"
                style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}
              >
                {isSubmitting ? t('common.loading') : t('common.submit')}
              </Button>
            </div>
          </form>
        </CardContent>
      </Card>

      {/* Guidelines Card */}
      <Card className="bg-white border-2 border-[#3A7FC2] shadow-md">
        <CardHeader className="bg-gray-50 border-b border-gray-200">
          <CardTitle className="text-[18px] font-semibold text-[#053A5F]" style={{ fontFamily: 'Open Sans, sans-serif' }}>
            Conseils pour une offre de qualité
          </CardTitle>
        </CardHeader>
        <CardContent className="space-y-4 p-6 bg-white">
          <div>
            <h4 className="font-semibold mb-2 text-[#053A5F] flex items-center" style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}>
              📝 Description claire et détaillée
            </h4>
            <p className="text-[#3A7FC2] leading-relaxed" style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}>
              Adaptez votre description selon le type d'offre. Pour les stages, précisez l'encadrement et les missions. 
              Pour les alternances, détaillez le programme de formation et le rythme. Pour les emplois, mentionnez les évolutions possibles.
            </p>
          </div>
          <div>
            <h4 className="font-semibold mb-2 text-[#053A5F] flex items-center" style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}>
              🎯 Type d'offre personnalisé
            </h4>
            <p className="text-[#3A7FC2] leading-relaxed" style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}>
              Si votre offre ne correspond à aucun type standard, utilisez "Autre" et spécifiez le type exact 
              (ex: Mission, Freelance, Projet, Bénévolat, Consulting).
            </p>
          </div>
          <div>
            <h4 className="font-semibold mb-2 text-[#053A5F] flex items-center" style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}>
              ⏰ Durée et modalités
            </h4>
            <p className="text-[#3A7FC2] leading-relaxed" style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}>
              Précisez la durée pour les stages et alternances. Pour les emplois, indiquez le type de contrat (CDI, CDD).
              Pour les types personnalisés, adaptez selon la nature de l'offre.
            </p>
          </div>
          <div>
            <h4 className="font-semibold mb-2 text-[#053A5F] flex items-center" style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}>
              📍 Localisation et télétravail
            </h4>
            <p className="text-[#3A7FC2] leading-relaxed" style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}>
              Indiquez clairement si le poste est en présentiel, télétravail complet, ou hybride.
            </p>
          </div>
          <div>
            <h4 className="font-semibold mb-2 text-[#053A5F] flex items-center" style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}>
              🔗 Lien externe utile
            </h4>
            <p className="text-[#3A7FC2] leading-relaxed" style={{ fontFamily: 'Open Sans, sans-serif', fontSize: '14px' }}>
              Si possible, ajoutez un lien vers l'offre originale pour faciliter les candidatures et obtenir plus de détails.
            </p>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
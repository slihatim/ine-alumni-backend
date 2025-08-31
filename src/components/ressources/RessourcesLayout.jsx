import { Outlet, useNavigate, useLocation } from "react-router";
import { useState, useEffect } from "react";
import { Tabs, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Input } from "@/components/ui/input";

const tabs = [
  { key: "textuelles", label: "Ressources textuelles" },
  { key: "interactives", label: "Ressources interactives" },
  { key: "outils", label: "Outils pratiques" },
  { key: "certification", label: "Ressources de certification" },
];

export default function RessourcesLayout() {
  const navigate = useNavigate();
  const location = useLocation();
  const [activeTab, setActiveTab] = useState("textuelles");

  const placeholderContent = (activeTab) => {
    switch (activeTab) {
      case "textuelles":
        return "Rechercher par titre, auteur, technologie...";
      case "interactives":
        return "Rechercher par nom, catégorie...";
      case "outils":
        return "Rechercher par nom, type...";
      case "certification":
        return "Rechercher par nom, domaine...";
      default:
        return "Rechercher...";
    }
  };

  useEffect(() => {
    // derive tab from current path (/ressources/:tab)
    const parts = location.pathname.split("/").filter(Boolean);
    const last = parts[parts.length - 1];
    if (tabs.find((t) => t.key === last)) {
      setActiveTab(last);
    } else if (
      location.pathname.endsWith("/ressources") ||
      location.pathname.endsWith("/ressources/")
    ) {
      setActiveTab("textuelles");
    }
  }, [location.pathname]);

  const handleTabChange = (value) => {
    setActiveTab(value);
    navigate(`/ressources/${value}`);
  };

  return (
    <>
      <div className="max-w-4xl mx-auto">
        <h1 className="text-xl font-bold mt-6 mb-1">Ressources</h1>
        <p className="text-gray-500 text-sm mt-1.5">
          Bénéficiez de notre collection de ressources variées pour enrichir
          votre apprentissage et maximiser votre réussite académique
        </p>
        <div className="mt-6">
          <Tabs value={activeTab} onValueChange={handleTabChange}>
            <TabsList>
              {tabs.map((tab) => (
                <TabsTrigger key={tab.key} value={tab.key}>
                  {tab.label}
                </TabsTrigger>
              ))}
            </TabsList>
          </Tabs>

          <div className="relative text-gray-600 flex flex-row justify-center mt-4 mb-6">
            <Input
              type="search"
              name="search"
              placeholder={placeholderContent(activeTab)}
              className="max-w-md"
            />
          </div>
        </div>
      </div>

      <Outlet />
      <div className="mb-14" />
    </>
  );
}

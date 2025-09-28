import { Card, CardContent } from "@/components/ui/card";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { Linkedin, Mail } from "lucide-react";
import { cn } from "@/lib/utils";
import { useNavigate } from "react-router";

const getInitials = (name) => {
  return name
    .split(" ")
    .map((n) => n[0])
    .join("")
    .toUpperCase()
    .slice(0, 2);
};

const profileLabels = {
  title: "Poste:",
  major: "Filière:",
  company: "Entreprise:",
  location: "Localisation:",
};

const ProfileDetail = ({ label, value }) => (
  <div>
    <span className="font-semibold text-gray-900">{label} </span>
    <span className="text-gray-700">{value}</span>
  </div>
);

export function ProfileCard({ profileData, className, compact = false }) {
  const profileFields = [
    { key: "title", label: profileLabels.title },
    { key: "major", label: profileLabels.major },
    { key: "company", label: profileLabels.company },
    { key: "location", label: profileLabels.location },
  ];
  const navigate = useNavigate();
  const handleLaureateClick = () => {
    navigate(`/laureats/${profileData.id}`);
  };
  if (compact) {
    return (
      <div
        className={cn(
          "flex items-center gap-3 p-4 hover:cursor-pointer",
          className
        )}
        onClick={handleLaureateClick}
      >
        <Avatar className="w-12 h-12 flex-shrink-0">
          <AvatarImage src={profileData.photoUrl} alt={profileData.name} />
          <AvatarFallback className="text-sm font-semibold">
            {getInitials(profileData.name)}
          </AvatarFallback>
        </Avatar>
        <div className="flex flex-col min-w-0 flex-1">
          <h3 className="font-semibold text-lg text-gray-900 transition-colors truncate">
            {profileData.name}
          </h3>
          <Badge className="text-xs text-gray-500 w-fit" variant="secondary">
            {profileData.title}
          </Badge>
        </div>
      </div>
    );
  }

  return (
    <Card
      className={cn(
        "hover:shadow-lg transition-all duration-200 cursor-pointer group",
        className
      )}
      onClick={handleLaureateClick}
    >
      <CardContent className="flex flex-col items-start gap-4">
        <div className="flex items-center gap-2">
          <Avatar className="w-20 h-20 flex-shrink-0">
            <AvatarImage src={profileData.photoUrl} alt={profileData.name} />
            <AvatarFallback className="text-lg font-semibold">
              {getInitials(profileData.name)}
            </AvatarFallback>
          </Avatar>
          <div className="flex flex-col">
            <h3 className="font-semibold text-xl text-gray-900 transition-colors">
              {profileData.name}
            </h3>
            <Badge className="text-sm text-gray-500" variant="secondary">
              Promotion {profileData.promotion}
            </Badge>
          </div>
        </div>
        <div className="w-full flex flex-col flex-1 space-y-3">
          <div className="space-y-2 text-sm">
            {profileFields.map(({ key, label }) => (
              <ProfileDetail key={key} label={label} value={profileData[key]} />
            ))}
          </div>
          <hr />
          <div className="w-full flex justify-between gap-2">
            <Button
              variant="outline"
              size="sm"
              className="w-1/2 flex items-center gap-2 bg-blue-50 hover:bg-blue-100 border-blue-200"
              onClick={(e) => {
                e.stopPropagation();
                window.open(profileData.linkedinUrl, "_blank");
              }}
            >
              <Linkedin className="w-4 h-4 text-main-blue" />
              <span className="text-main-blue">LinkedIn</span>
            </Button>
            <Button
              variant="outline"
              size="sm"
              className="w-1/2 flex items-center gap-2 bg-gray-100 hover:bg-gray-200"
              onClick={(e) => {
                e.stopPropagation();
                window.open(`mailto:${profileData.email}`, "_blank");
              }}
            >
              <Mail className="w-4 h-4" />
              Email
            </Button>
          </div>
        </div>
      </CardContent>
    </Card>
  );
}

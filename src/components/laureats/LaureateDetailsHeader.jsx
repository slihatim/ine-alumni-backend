import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Linkedin, Mail, MapPin, Building } from "lucide-react";
import { cn } from "@/lib/utils";

export function LaureateDetailsHeader({
  name,
  photoUrl,
  title,
  major,
  company,
  location,
  className,
}) {
  const getInitials = (name) => {
    return name
      .split(" ")
      .map((n) => n[0])
      .join("")
      .toUpperCase()
      .slice(0, 2);
  };

  return (
    <div className={cn("bg-white rounded-lg shadow-sm border p-6", className)}>
      <div className="flex flex-col md:flex-row items-center md:items-start gap-6">
        <Avatar className="w-32 h-32">
          <AvatarImage src={photoUrl} alt={name} />
          <AvatarFallback className="text-2xl font-semibold">
            {getInitials(name)}
          </AvatarFallback>
        </Avatar>
        <div className="flex-1 text-center md:text-left space-y-4">
          <div>
            <h1 className="text-2xl font-bold text-gray-900 mb-2">{name}</h1>
            <p className="text-lg text-gray-700 mb-1">{title}</p>
            <p className="text-gray-600">{major}</p>
          </div>

          <div className="flex flex-col sm:flex-row sm:items-center gap-4 text-gray-600">
            <div className="flex items-center gap-2">
              <Building className="w-4 h-4" />
              <span>{company}</span>
            </div>
            <div className="flex items-center gap-2">
              <MapPin className="w-4 h-4" />
              <span>{location}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

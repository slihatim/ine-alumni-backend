import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { MapPin, ExternalLink } from "lucide-react";
import { cn } from "@/lib/utils";

export function CompanyDetailsHeader({
  name,
  logoUrl,
  location,
  website,
  domain,
  className,
}) {
  return (
    <div className={cn("bg-white rounded-lg shadow-sm border p-6", className)}>
      <div className="flex flex-col md:flex-row items-center md:items-start gap-6">
        {/* Company Logo */}
        <div className="w-32 h-32 bg-gray-900 rounded-lg flex items-center justify-center overflow-hidden">
          {logoUrl ? (
            <img
              src={logoUrl}
              alt={`${name} logo`}
              className="w-full h-full object-contain p-4"
            />
          ) : (
            <div className="text-white font-bold text-3xl">
              {name
                .split(" ")
                .map((word) => word[0])
                .join("")
                .slice(0, 2)}
            </div>
          )}
        </div>

        {/* Company Info */}
        <div className="flex-1 text-center md:text-left space-y-4">
          <div>
            <h1 className="text-3xl font-bold text-gray-900 mb-2">{name}</h1>
            <Badge variant="outline" className="mb-3">
              {domain}
            </Badge>
          </div>

          <div className="flex flex-col sm:flex-row sm:items-center gap-4 text-gray-600">
            <div className="flex items-center gap-2">
              <MapPin className="w-4 h-4" />
              <span>{location}</span>
            </div>
            {website && (
              <div className="flex items-center gap-2">
                <ExternalLink className="w-4 h-4" />
                <a
                  href={website}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="text-main-blue hover:underline"
                >
                  {website.replace(/^https?:\/\//, "")}
                </a>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

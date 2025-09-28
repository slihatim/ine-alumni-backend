import { ProfileCard } from "@/components/laureats/ProfileCard";
import { cn } from "@/lib/utils";

export function AlumniList({ alumni, onAlumniClick, className }) {
  return (
    <div className={cn("space-y-4", className)}>
      <h3 className="text-lg font-semibold text-gray-900 mb-4">
        Lauréats dans cette entreprise
      </h3>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {alumni.map((alumnus) => (
          <ProfileCard
            key={alumnus.id}
            profileData={alumnus}
            onClick={() => onAlumniClick(alumnus)}
            className="h-fit"
            compact={true}
          />
        ))}
      </div>
    </div>
  );
}

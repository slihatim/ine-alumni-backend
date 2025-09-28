import { Badge } from "@/components/ui/badge";
import { cn } from "@/lib/utils";

export function SkillsList({ skills, className }) {
  return (
    <div className={cn("", className)}>
      <div className="flex flex-wrap gap-2">
        {skills.map((skill, index) => (
          <Badge
            key={index}
            variant="outline"
            className="text-sm py-1 px-3 hover:bg-blue-50 hover:border-blue-300 transition-colors"
          >
            {skill}
          </Badge>
        ))}
      </div>
    </div>
  );
}

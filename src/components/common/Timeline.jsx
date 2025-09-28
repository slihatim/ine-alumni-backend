import { Badge } from "@/components/ui/badge";
import { Calendar } from "lucide-react";
import { cn } from "@/lib/utils";

export function Timeline({ items, className }) {
  const formatDateRange = (startDate, endDate, current) => {
    if (current) {
      return `${startDate} - Présent`;
    }
    return endDate ? `${startDate} - ${endDate}` : startDate;
  };

  return (
    <div className={cn("space-y-8", className)}>
      {items.map((item) => (
        <div className="ml-8 space-y-3">
          <div>
            <h3 className="text-lg font-semibold text-gray-900">
              {item.title}
            </h3>
            <p className="text-main-blue font-medium">{item.subtitle}</p>
          </div>

          <div className="flex items-center gap-2 text-gray-600">
            <Calendar className="w-4 h-4" />
            <span className="text-sm">
              {formatDateRange(item.startDate, item.endDate, item.current)}
            </span>
            {item.current && (
              <Badge variant="secondary" className="text-xs">
                Actuel
              </Badge>
            )}
          </div>

          {item.description && (
            <p className="text-gray-700 text-sm leading-relaxed">
              {item.description}
            </p>
          )}
        </div>
      ))}
    </div>
  );
}

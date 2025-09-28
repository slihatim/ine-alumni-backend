import { Card, CardContent } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { cn } from "@/lib/utils";

export function CompanyCard({
  name,
  logoUrl,
  domain,
  laureatesCount,
  className,
  onClick,
}) {
  return (
    <Card
      className={cn(
        "hover:shadow-lg transition-all duration-200 cursor-pointer group aspect-square",
        className
      )}
      onClick={onClick}
    >
      <CardContent className="p-1 h-full flex flex-col items-center justify-center text-center space-y-3">
        <div className="w-16 h-16 bg-gray-900 rounded flex items-center justify-center overflow-hidden">
          {logoUrl ? (
            <img
              src={logoUrl}
              alt={`${name} logo`}
              className="w-full h-full object-contain"
            />
          ) : (
            <div className="text-white font-bold text-lg">
              {name
                .split(" ")
                .map((word) => word[0])
                .join("")
                .slice(0, 2)}
            </div>
          )}
        </div>

        <h3 className="font-semibold text-base text-gray-900 group-hover:text-main-blue transition-colors">
          {name}
        </h3>

        <Badge variant="outline" className="text-xs">
          {domain}
        </Badge>

        <p className="text-sm text-gray-600">
          <span className="font-medium text-green-600">{laureatesCount}</span>{" "}
          lauréat{laureatesCount > 1 ? "s" : ""}
        </p>
      </CardContent>
    </Card>
  );
}

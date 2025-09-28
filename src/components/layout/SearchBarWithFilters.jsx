import { useState } from "react";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Search, ListFilter } from "lucide-react";
import { cn } from "@/lib/utils";

export function SearchBarWithFilters({
  placeholder,
  filters,
  onSearch,
  showFilters = true,
  className,
}) {
  const [searchQuery, setSearchQuery] = useState("");
  const [showFiltersPanel, setShowFiltersPanel] = useState(false);
  const [sortValue, setSortValue] = useState("name");

  const handleSearchChange = (e) => {
    const query = e.target.value;
    setSearchQuery(query);
    onSearch(query);
  };

  return (
    <div className={cn("space-y-4 bg-gray-50 p-6 rounded-lg", className)}>
      {/* Search Bar */}
      <div className="flex gap-2">
        <div className="relative flex-1 bg-white">
          <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-4 h-4" />
          <Input
            type="text"
            placeholder={placeholder}
            value={searchQuery}
            onChange={handleSearchChange}
            className="pl-10"
          />
        </div>

        {showFilters && filters && (
          <>
            <div className="flex items-center gap-2 ">
              <label htmlFor="sort" className="text-sm text-gray-600">
                Trier par :
              </label>
              <Select value={sortValue} onValueChange={setSortValue}>
                <SelectTrigger className="w-[140px] text-sm bg-white">
                  <SelectValue placeholder="Sélectionner..." />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="name">Nom</SelectItem>
                  <SelectItem value="promotion">Promotion</SelectItem>
                  <SelectItem value="company">Entreprise</SelectItem>
                </SelectContent>
              </Select>
            </div>
            <Button
              variant="outline"
              onClick={() => setShowFiltersPanel(!showFiltersPanel)}
              className="px-3 bg-main-blue !text-white hover:bg-secondary-blue"
            >
              <ListFilter className="w-4 h-4 mr-2" />
              Filtres avancés
            </Button>
          </>
        )}
      </div>

      <div
        className={`overflow-hidden transition-all duration-300 ease-in-out ${
          showFiltersPanel ? "max-h-96 opacity-100" : "max-h-0 opacity-0"
        }`}
      >
        {filters && <>{filters}</>}
      </div>
    </div>
  );
}

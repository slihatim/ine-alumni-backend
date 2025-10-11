import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { X } from "lucide-react";
import { cn } from "@/lib/utils";

// TODO: Implement filter logic and state management
// This component currently shows UI placeholders for filters
// Need to implement:
// - Filter state management
// - Filter update logic
// - Clear filter functionality
// - Active filters tracking
// - onChange handler integration

export function FilterPanel({ filters, onChange, className }) {
  // TODO: Implement filter rendering logic with proper state management and event handling
  const renderFilter = (filter) => {
    switch (filter.type) {
      case "select":
        return (
          <div key={filter.key} className="space-y-2">
            <label className="text-sm font-medium text-gray-700">
              {filter.label}
            </label>
            <Select
              onValueChange={(value) =>
                onChange({ type: filter.type, key: filter.key, value })
              }
            >
              <SelectTrigger className="w-full">
                <SelectValue placeholder="Tous" />
              </SelectTrigger>
              <SelectContent>
                {filter.options?.map((option) => (
                  <SelectItem key={option.value} value={option.value}>
                    {option.label}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>
        );

      case "multiselect":
        return (
          <div key={filter.key} className="space-y-2">
            <label className="text-sm font-medium text-gray-700">
              {filter.label}
            </label>
            <div className="space-y-2">
              {filter.options?.map((option) => (
                <label
                  key={option.value}
                  className="flex items-center space-x-2"
                >
                  <input
                    type="checkbox"
                    onChange={(e) =>
                      onChange({
                        type: filter.type,
                        key: filter.key,
                        value: option.value,
                        checked: e.target.checked,
                      })
                    }
                    className="rounded border-gray-300 text-main-blue focus:ring-blue-500"
                  />
                  <span className="text-sm text-gray-700">{option.label}</span>
                </label>
              ))}
            </div>
          </div>
        );

      case "checkbox":
        return (
          <div key={filter.key} className="space-y-2">
            <label className="flex items-center space-x-2">
              <input
                type="checkbox"
                onChange={(e) =>
                  onChange({
                    type: filter.type,
                    key: filter.key,
                    checked: e.target.checked,
                  })
                }
                className="rounded border-gray-300 text-main-blue focus:ring-blue-500"
              />
              <span className="text-sm font-medium text-gray-700">
                {filter.label}
              </span>
            </label>
          </div>
        );

      default:
        return null;
    }
  };

  return (
    <div className={cn("space-y-6", className)}>
      {/* Filter Controls */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {filters.map(renderFilter)}
      </div>

      {/* TODO: Implement active filters display with proper state management */}
      {/* This section will show selected filters and allow clearing them */}
      <div className="space-y-2">
        <div className="flex items-center justify-between">
          <h4 className="text-sm font-medium text-gray-700">Filtres actifs:</h4>
          <Button variant="ghost" size="sm" disabled className="text-gray-400">
            Effacer tout
          </Button>
        </div>

        <div className="flex flex-wrap gap-2">
          <Badge
            variant="secondary"
            className="flex items-center gap-1 opacity-50"
          >
            <span className="text-xs">Exemple de filtre</span>
            <button disabled className="ml-1 rounded-full p-0.5">
              <X className="w-3 h-3" />
            </button>
          </Badge>
        </div>
      </div>
    </div>
  );
}

import { useState } from "react";
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

export function FilterPanel({ filters, onChange, className }) {
  const [activeFilters, setActiveFilters] = useState({});

  const updateFilter = (key, value) => {
    const newFilters = { ...activeFilters, [key]: value };
    if (
      value === null ||
      value === undefined ||
      value === "" ||
      (Array.isArray(value) && value.length === 0)
    ) {
      delete newFilters[key];
    }
    setActiveFilters(newFilters);
    onChange(newFilters);
  };

  const clearFilter = (key) => {
    const newFilters = { ...activeFilters };
    delete newFilters[key];
    setActiveFilters(newFilters);
    onChange(newFilters);
  };

  const clearAllFilters = () => {
    setActiveFilters({});
    onChange({});
  };

  const hasActiveFilters = Object.keys(activeFilters).length > 0;

  const renderFilter = (filter) => {
    const value = activeFilters[filter.key];

    switch (filter.type) {
      case "select":
        return (
          <div key={filter.key} className="space-y-2">
            <label className="text-sm font-medium text-gray-700">
              {filter.label}
            </label>
            <Select
              value={value || undefined}
              onValueChange={(selectedValue) => {
                updateFilter(filter.key, selectedValue);
              }}
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

      case "multiselect": {
        const selectedValues = value || [];
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
                    checked={selectedValues.includes(option.value)}
                    onChange={(e) => {
                      const newValues = e.target.checked
                        ? [...selectedValues, option.value]
                        : selectedValues.filter((v) => v !== option.value);
                      updateFilter(filter.key, newValues);
                    }}
                    className="rounded border-gray-300 text-main-blue focus:ring-blue-500"
                  />
                  <span className="text-sm text-gray-700">{option.label}</span>
                </label>
              ))}
            </div>
          </div>
        );
      }

      case "checkbox":
        return (
          <div key={filter.key} className="space-y-2">
            <label className="flex items-center space-x-2">
              <input
                type="checkbox"
                checked={value || false}
                onChange={(e) => updateFilter(filter.key, e.target.checked)}
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

      {/* Active Filters Display */}
      {hasActiveFilters && (
        <div className="space-y-2">
          <div className="flex items-center justify-between">
            <h4 className="text-sm font-medium text-gray-700">
              Filtres actifs:
            </h4>
            <Button
              variant="ghost"
              size="sm"
              onClick={clearAllFilters}
              className="text-red-600 hover:text-red-700"
            >
              Effacer tout
            </Button>
          </div>

          <div className="flex flex-wrap gap-2">
            {Object.entries(activeFilters).map(([key, value]) => {
              const filter = filters.find((f) => f.key === key);
              if (!filter) return null;

              const displayValue = Array.isArray(value)
                ? value.join(", ")
                : filter.options?.find((opt) => opt.value === value)?.label ||
                  value;

              return (
                <Badge
                  key={key}
                  variant="secondary"
                  className="flex items-center gap-1"
                >
                  <span className="text-xs">
                    {filter.label}: {displayValue}
                  </span>
                  <button
                    onClick={() => clearFilter(key)}
                    className="ml-1 hover:bg-gray-200 rounded-full p-0.5"
                  >
                    <X className="w-3 h-3" />
                  </button>
                </Badge>
              );
            })}
          </div>
        </div>
      )}
    </div>
  );
}

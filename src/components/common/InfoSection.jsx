import { cn } from "@/lib/utils";

export function InfoSection({ title, content, className }) {
  return (
    <div className={cn("space-y-3", className)}>
      <h2 className="text-lg font-semibold text-gray-900 border-b border-gray-200 pb-2">
        {title}
      </h2>
      <div className="text-gray-700">{content}</div>
    </div>
  );
}

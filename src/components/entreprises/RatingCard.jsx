import { Card, CardContent } from "@/components/ui/card";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Star } from "lucide-react";
import { cn } from "@/lib/utils";

export function RatingCard({
  author,
  timeAgo,
  rating,
  comment,
  authorAvatar,
  className,
}) {
  const renderStars = (rating) => {
    return Array.from({ length: 5 }, (_, index) => (
      <Star
        key={index}
        className={cn(
          "w-4 h-4",
          index < rating ? "fill-yellow-400 text-yellow-400" : "text-gray-300"
        )}
      />
    ));
  };

  const getInitials = (name) => {
    return name
      .split(" ")
      .map((n) => n[0])
      .join("")
      .toUpperCase()
      .slice(0, 2);
  };

  return (
    <div className={cn("p-6", className)}>
      <div className="space-y-4">
        {/* Author and Rating Header */}
        <div className="flex items-start justify-between">
          <div className="flex items-center gap-3">
            <Avatar className="w-10 h-10">
              <AvatarImage src={authorAvatar} alt={author} />
              <AvatarFallback className="text-sm font-medium">
                {getInitials(author)}
              </AvatarFallback>
            </Avatar>
            <div>
              <p className="font-medium text-gray-900">{author}</p>
              <p className="text-sm text-gray-500">{timeAgo}</p>
            </div>
          </div>

          {/* Star Rating */}
          <div className="flex items-center gap-1">{renderStars(rating)}</div>
        </div>

        {/* Comment */}
        <p className="text-gray-700 text-sm leading-relaxed">{comment}</p>
      </div>
    </div>
  );
}

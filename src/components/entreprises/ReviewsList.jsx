import { RatingCard } from "@/components/entreprises/RatingCard";
import { cn } from "@/lib/utils";

export function ReviewsList({ reviews, className }) {
  return (
    <div className={cn("space-y-4", className)}>
      <h3 className="text-lg font-semibold text-gray-900 mb-4">
        Avis des lauréats
      </h3>
      <div className="space-y-4">
        {reviews.map((review) => (
          <RatingCard
            key={review.id}
            author={review.author}
            timeAgo={review.timeAgo}
            rating={review.rating}
            comment={review.comment}
            authorAvatar={review.authorAvatar}
          />
        ))}
      </div>
    </div>
  );
}

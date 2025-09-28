import { Button } from "@/components/ui/button";
import { Mail, Linkedin } from "lucide-react";
import { cn } from "@/lib/utils";

export function HRContactSection({ contacts, className }) {
  return (
    <div className={cn("space-y-4", className)}>
      {contacts.map((contact, index) => (
        <div className="p-4" key={index}>
          <div className="flex items-center justify-between">
            <div>
              <h4 className="font-medium text-gray-900">{contact.title}</h4>
              <p className="text-sm text-gray-600">{contact.description}</p>
            </div>
            <div className="flex gap-2">
              {contact.email && (
                <Button
                  variant="outline"
                  size="sm"
                  onClick={() =>
                    window.open(`mailto:${contact.email}`, "_blank")
                  }
                >
                  <Mail className="w-4 h-4" />
                </Button>
              )}
              {contact.linkedin && (
                <Button
                  variant="outline"
                  size="sm"
                  onClick={() => window.open(contact.linkedin, "_blank")}
                >
                  <Linkedin className="w-4 h-4" />
                </Button>
              )}
            </div>
          </div>
        </div>
      ))}
    </div>
  );
}

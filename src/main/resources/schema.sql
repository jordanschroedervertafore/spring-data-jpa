CREATE TABLE IF NOT EXISTS public.producer
(
    id uuid NOT NULL PRIMARY KEY,
    name text,
    email text,
    home_state text,
    npn text,
    version TIMESTAMP WITH TIME ZONE
);

CREATE TABLE public.address (
    producer_id uuid PRIMARY KEY,
    address text,
    FOREIGN KEY (producer_id) REFERENCES public.producer
);

CREATE TABLE IF NOT EXISTS public.business_unit
(
    id uuid NOT NULL,
    name text,
    version int,
    CONSTRAINT business_unit_pkey PRIMARY KEY (id)
);

CREATE TABLE public.producer_business_unit
(
    producer_id uuid,
    business_unit_id uuid,
    FOREIGN KEY (producer_id)
        REFERENCES public.producer (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE,
    FOREIGN KEY (business_unit_id)
        REFERENCES public.business_unit (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
        DEFERRABLE
);

CREATE TABLE public.license
(
    id uuid,
    producer_id uuid,
    license_number text,
    FOREIGN KEY (producer_id)
        REFERENCES public.producer (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        DEFERRABLE
);

